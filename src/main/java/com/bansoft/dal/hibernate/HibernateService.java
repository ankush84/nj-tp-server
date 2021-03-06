package com.bansoft.dal.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.bansoft.Production.dal.ProductionEntity;
import com.bansoft.ProductionStock.dal.ProductionStockEntity;
import com.bansoft.Purchase.dal.PurchaseEntity;
import com.bansoft.Sale.dal.SaleEntity;
import com.bansoft.Stock.dal.StockEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class HibernateService {
    private SessionFactory sessionFactory;

    public HibernateService() {
        init();
    }

    public void init() {
        getSessionFactory();
    }

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = initProperties();
                configuration.setProperties(settings);

                configuration.addAnnotatedClass(PurchaseEntity.class);
                configuration.addAnnotatedClass(StockEntity.class);
                configuration.addAnnotatedClass(ProductionEntity.class);
                configuration.addAnnotatedClass(ProductionStockEntity.class);
                configuration.addAnnotatedClass(SaleEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private Properties initProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/njtpdb?allowPublicKeyRetrieval=true&useSSL=false");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        return properties;
    }

    public boolean save(Object entity) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.saveOrUpdate(entity);
            // commit transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public <T> List<T> getAll(Class<T> clazz, String name) {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from " + name, clazz).list();
        }
    }

    public <T> List<T> getAllWhere(Class<T> clazz, String name, String whereClause, HashMap<String, Object> params) {
        try (Session session = getSessionFactory().openSession()) {
            Query<T> query = session.createQuery("from " + name + " where " + whereClause, clazz);
            for (String key : params.keySet()) {

                query.setParameter(key, params.get(key));

            }
            return query.list();
        }
    }
}