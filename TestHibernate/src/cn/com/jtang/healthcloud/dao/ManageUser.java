package cn.com.jtang.healthcloud.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import java.util.List;
import cn.com.jtang.healthcloud.pojo.User;

public class ManageUser {

    // BuildSessionFactory() deprecated in hibernate 4
    // using buildSessionFactory(ServiceRegistry serviceRegistry) instead
    private static SessionFactory factory;
    public ManageUser() {
        try {
        	factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // INSERT a record into table or UPDATE a existing record in table
    public boolean addOrUpdateUser(User user) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            String hql = "FROM User U WHERE U.openId = :openId";
            Query query = session.createQuery(hql);
            query.setParameter("openId", user.getOpenId());
            List result = query.list();
            if (result.size() == 0) {
                session.save(user);
            } else {
                User u = (User) result.get(0);
                u.setSubscribe(user.getSubscribe()).setOpenId(user.getOpenId());
                u.setNickname(user.getNickname()).setSex(user.getSex());
                u.setAge(user.getAge()).setLanguage(user.getLanguage());
                u.setRealName(user.getRealName()).setCardId(user.getCardId());
                u.setTelephone(user.getTelephone()).setAvatarUrl(user.getAvatarUrl());
                u.setCity(user.getCity()).setProvince(user.getProvince());
                u.setCountry(user.getCountry()).setSubscribeTime(user.getSubscribeTime());
                u.setUnionId(user.getUnionId()).setRemark(user.getRemark());
                u.setGroupId(user.getGroupId());
                session.update(u);
            }
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

    // DELETE a record from table
    public boolean deleteUser(String openId) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            String hql = "FROM User U WHERE U.openId = :openId";
            Query query = session.createQuery(hql);
            query.setParameter("openId", openId);
            List result = query.list();
            User user = (User) session.get(User.class, ((User) result.get(0)).getUserId());
            session.delete(user);
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

    // LIST all the records and RETURN
    public List<User> listUser() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = null;
        try {
            tx = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }
    
    // SELECT n rows
    public List<User> listUser(int startRow, int numbers) {
    	Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = null;
        try {
        	tx = session.beginTransaction();
        	Query query = session.createQuery("FROM User");
        	query.setFirstResult(startRow-1);
        	query.setMaxResults(numbers);
        	users = query.list();
        	tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    // SELECT a record in table
    public User selectUser(String openId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> user = null;
        try {
            tx = session.beginTransaction();
            String hql = "FROM User u WHERE u.openId = :openId";
            Query query = session.createQuery(hql);
            query.setParameter("openId", openId);
            user = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user.size()==0 ? null : user.get(0);
    }
}
