package com.bansoft.ProductionStock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.bansoft.ProductionStock.comm.ProductionStockTopic;
import com.bansoft.ProductionStock.dal.ProductionStockDao;
import com.bansoft.ProductionStock.dal.ProductionStockEntity;
import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.ProductionStock.model.IProductionStockBuilder;
import com.bansoft.ProductionStock.model.ProductionStockBuilder;
import com.bansoft.dal.hibernate.HibernateService;

public class ProductionStockService implements IProductionStockService {

    private HashMap<String, HashMap<Long, IProductionStock>> cacheByProductName;
    private ProductionStockDao dao;
    private ProductionStockTopic ProductionStockTopic;
    final double THRESHOLD = .001;

    public ProductionStockService(HibernateService hibernateService) {
        this.cacheByProductName = new HashMap<>();
        this.dao = new ProductionStockDao(hibernateService);
        ProductionStockTopic = new ProductionStockTopic(this);
        this.init();
    }

    private void init() {
        List<ProductionStockEntity> entities = this.dao.loadAllAvailableProductionStocks();
        for (ProductionStockEntity entity : entities) {
            IProductionStock model = ProductionStockConverter.fromProductionStockEntityToModel(entity);
            // this.cache.put(entity.getId(), model);
            updateCache(model);
        }
    }

    @Override
    public IProductionStockBuilder newProductionStock() {
        return new ProductionStockBuilder();
    }

    @Override
    public void commitProductionStock(IProductionStock ProductionStock) {
        ProductionStockEntity entity = ProductionStockConverter.fromProductionStockModelToEntity(ProductionStock);
        dao.save(entity);
        ProductionStock.setId(entity.getId());
        updateCache(ProductionStock);
        this.ProductionStockTopic.supplyAdd(ProductionStockConverter.fromProductionStockModelToSupply(ProductionStock));
    }

    @Override
    public IProductionStock[] getAllProductionStocks() {
        List<IProductionStock> ProductionStocksToSupply = new LinkedList<>();
        for (HashMap<Long,IProductionStock> map : cacheByProductName.values()) {
            ProductionStocksToSupply.addAll(map.values());
        }

        return ProductionStocksToSupply.toArray(new IProductionStock[0]);
    }

    @Override
    public IProductionStock[] takeOutProductionStocksForProduct(String productName, Double requiredQty) {

        if (!cacheByProductName.containsKey(productName)) {
            // out of ProductionStock
            return null;
        }
        LinkedList<IProductionStock> ProductionStocksToTakeOut = new LinkedList<>();
        LinkedList<IProductionStock> sortedProductionStocks = new LinkedList<>();
        IProductionStock ProductionStockToSlice = null;
        Double qtyToSlice = null;
        HashMap<Long, IProductionStock> ProductionStocks = cacheByProductName.get(productName);
        ProductionStocks.values().stream().sorted(new TimestampSorter()).forEach(stk -> {
            sortedProductionStocks.add(stk);
        });

        for (IProductionStock stk : sortedProductionStocks) {
            if (requiredQty < THRESHOLD)
                break;

            if (stk.getQty() < requiredQty) {
                ProductionStocksToTakeOut.add(stk);
                requiredQty = requiredQty - stk.getQty();
            } else {
                // update ProductionStock
                // stk.setQty(stk.getQty() - requiredQty);
                ProductionStockToSlice = stk;
                qtyToSlice = requiredQty;
                requiredQty = THRESHOLD;
                break;
            }
        }

        if (requiredQty > THRESHOLD) {
            // out of ProductionStock
            return null;
        }

        // we found sufficient qty, lets takeout now, we'll only update cache, will
        // commit once we consume
        for (IProductionStock stk : ProductionStocksToTakeOut) {
            // remove from cache
            cacheByProductName.get(productName).remove(stk.getId());
        }

        IProductionStock slicedProductionStock = newProductionStock().Id(ProductionStockToSlice.getId())
        .productName(ProductionStockToSlice.getProductName())
                .lotNumber(ProductionStockToSlice.getLotNumber()).qty(qtyToSlice)
                .timestamp(ProductionStockToSlice.getTimestamp())
                .build();
        ProductionStocksToTakeOut.add(slicedProductionStock);

        // update cache.
        ProductionStockToSlice.setQty(ProductionStockToSlice.getQty() - qtyToSlice);

        return ProductionStocksToTakeOut.toArray(new IProductionStock[0]);
    }

    @Override
    public void restoreProductionStocks(String productName, IProductionStock[] ProductionStocks) {
        HashMap<Long, IProductionStock> map = cacheByProductName.get(productName);
        for (IProductionStock stk : ProductionStocks) {
            if (map.containsKey(stk.getId())) {
                // its a sliced ProductionStock
                IProductionStock inMemProductionStock = map.get(stk.getId());
                inMemProductionStock.setQty(inMemProductionStock.getQty() + stk.getQty());
            } else {
                map.put(stk.getId(), stk);
            }
        }
    }

    @Override
    public void consumeProductionStock(IProductionStock ProductionStock) {
        ProductionStockEntity entity = dao.loadById(ProductionStock.getId());
        Double remainingQty = entity.getQty() - ProductionStock.getQty();
        if (remainingQty > THRESHOLD) {
            entity.setQty(remainingQty);
        } else {
            entity.setQty(0.0);
        }
        dao.save(entity);

        ProductionStock = ProductionStockConverter.fromProductionStockEntityToModel(entity);
        if (ProductionStock.getQty() > THRESHOLD) {
            this.ProductionStockTopic.supplyUpdate(ProductionStockConverter.fromProductionStockModelToSupply(ProductionStock));
        } else {
            this.ProductionStockTopic.supplyRemove(ProductionStockConverter.fromProductionStockModelToSupply(ProductionStock));
        }
    }

    private void updateCache(IProductionStock model) {
        if (!this.cacheByProductName.containsKey(model.getProductName())) {
            this.cacheByProductName.put(model.getProductName(), new HashMap<Long, IProductionStock>());
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

class TimestampSorter implements Comparator<IProductionStock> {
    public int compare(IProductionStock o1, IProductionStock o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
