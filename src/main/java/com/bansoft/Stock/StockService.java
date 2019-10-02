package com.bansoft.Stock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.bansoft.Stock.comm.StockTopic;
import com.bansoft.Stock.dal.StockDao;
import com.bansoft.Stock.dal.StockEntity;
import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.IStockBuilder;
import com.bansoft.Stock.model.StockBuilder;
import com.bansoft.dal.hibernate.HibernateService;

public class StockService implements IStockService {

    private HashMap<String, HashMap<Long, IStock>> cacheByProductName;
    private StockDao dao;
    private StockTopic StockTopic;
    final double THRESHOLD = .001;

    public StockService(HibernateService hibernateService) {
        this.cacheByProductName = new HashMap<>();
        this.dao = new StockDao(hibernateService);
        StockTopic = new StockTopic(this);
        this.init();
    }

    private void init() {
        List<StockEntity> entities = this.dao.loadAllAvailableStocks();
        for (StockEntity entity : entities) {
            IStock model = StockConverter.fromStockEntityToModel(entity);
            // this.cache.put(entity.getId(), model);
            updateCache(model);
        }
    }

    @Override
    public IStockBuilder newStock() {
        return new StockBuilder();
    }

    @Override
    public void commitStock(IStock stock) {
        StockEntity entity = StockConverter.fromStockModelToEntity(stock);
        dao.save(entity);
        stock.setId(entity.getId());
        updateCache(stock);
        this.StockTopic.supplyAdd(StockConverter.fromStockModelToSupply(stock));
    }

    @Override
    public IStock getStockByPurchaseId(Long id) {
        List<StockEntity> stocks = this.dao.loadStocksByPurchaseId(id);
        if (stocks == null || stocks.size() < 1) {
            return null;
        } else {
            return StockConverter.fromStockEntityToModel(stocks.get(0));
        }
    }

    @Override
    public IStock[] getAllStocks() {
        List<IStock> stocksToSupply = new LinkedList<>();
        for (HashMap<Long,IStock> map : cacheByProductName.values()) {
            stocksToSupply.addAll(map.values());
        }

        return stocksToSupply.toArray(new IStock[0]);
    }

    @Override
    public IStock[] takeOutStocksForProduct(String productName, Double requiredQty) {

        if (!cacheByProductName.containsKey(productName)) {
            // out of stock
            return null;
        }
        LinkedList<IStock> stocksToTakeOut = new LinkedList<>();
        LinkedList<IStock> sortedStocks = new LinkedList<>();
        IStock stockToSlice = null;
        Double qtyToSlice = null;
        HashMap<Long, IStock> stocks = cacheByProductName.get(productName);
        stocks.values().stream().sorted(new TimestampSorter()).forEach(stk -> {
            sortedStocks.add(stk);
        });

        for (IStock stk : sortedStocks) {
            if (requiredQty < THRESHOLD)
                break;

            if (stk.getQty() < requiredQty) {
                stocksToTakeOut.add(stk);
                requiredQty = requiredQty - stk.getQty();
            } else {
                // update stock
                // stk.setQty(stk.getQty() - requiredQty);
                stockToSlice = stk;
                qtyToSlice = requiredQty;
                requiredQty = THRESHOLD;
                break;
            }
        }

        if (requiredQty > THRESHOLD) {
            // out of stock
            return null;
        }

        // we found sufficient qty, lets takeout now, we'll only update cache, will
        // commit once we consume
        for (IStock stk : stocksToTakeOut) {
            // remove from cache
            cacheByProductName.get(productName).remove(stk.getId());
        }

        IStock slicedStock = newStock().Id(stockToSlice.getId()).productName(stockToSlice.getProductName())
                .purchaseId(stockToSlice.getPurchaseId()).qty(qtyToSlice).timestamp(stockToSlice.getTimestamp())
                .build();
        stocksToTakeOut.add(slicedStock);

        // update cache.
        stockToSlice.setQty(stockToSlice.getQty() - qtyToSlice);

        return stocksToTakeOut.toArray(new IStock[0]);
    }

    @Override
    public void restoreStocks(String productName, IStock[] stocks) {
        HashMap<Long, IStock> map = cacheByProductName.get(productName);
        for (IStock stk : stocks) {
            if (map.containsKey(stk.getId())) {
                // its a sliced stock
                IStock inMemStock = map.get(stk.getId());
                inMemStock.setQty(inMemStock.getQty() + stk.getQty());
            } else {
                map.put(stk.getId(), stk);
            }
        }
    }

    @Override
    public void consumeStock(IStock stock) {
        StockEntity entity = dao.loadById(stock.getId());
        Double remainingQty = entity.getQty() - stock.getQty();
        if (remainingQty > THRESHOLD) {
            entity.setQty(remainingQty);
        } else {
            entity.setQty(0.0);
        }
        dao.save(entity);

        stock = StockConverter.fromStockEntityToModel(entity);
        if (stock.getQty() > THRESHOLD) {
            this.StockTopic.supplyUpdate(StockConverter.fromStockModelToSupply(stock));
        } else {
            this.StockTopic.supplyRemove(StockConverter.fromStockModelToSupply(stock));
        }
    }

    private void updateCache(IStock model) {
        if (!this.cacheByProductName.containsKey(model.getProductName())) {
            this.cacheByProductName.put(model.getProductName(), new HashMap<Long, IStock>());
        }

        if (model.getQty() > THRESHOLD) {
            this.cacheByProductName.get(model.getProductName()).put(model.getId(), model);
        } else {
            if (this.cacheByProductName.get(model.getProductName()).containsKey(model.getId())) {
                this.cacheByProductName.get(model.getProductName()).remove(model.getId());
            }
        }
    }
}

class TimestampSorter implements Comparator<IStock> {
    public int compare(IStock o1, IStock o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
