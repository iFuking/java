package cn.com.jtang.healthcloud.dao;

import org.slf4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import cn.com.jtang.healthcloud.pojo.Cache;
import java.util.List;

public class ManageCache {

    private static Logger logger;

    private static SessionFactory factory;
    public ManageCache() {
        try {
        	factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // INSERT a cache record
    public boolean addCache(Cache cache) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            session.save(cache);
            tx.commit();
            success = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }
    
    // SELECT a cache record
    public Cache selectCache(String reportId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Cache> cache = null;
        try {
            tx = session.beginTransaction();
            String hql = "FROM Cache c WHERE c.reportId = :reportId";
            Query query = session.createQuery(hql);
            query.setParameter("reportId", reportId);
            cache = query.list();
            if (cache.size() == 0) System.out.println("No such Cache record, reportId='"+reportId+"'");
            else System.out.println("SELECT a Cache record, reportId='"+reportId+"'");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cache.size()==0 ? null : cache.get(0);
    }
}
