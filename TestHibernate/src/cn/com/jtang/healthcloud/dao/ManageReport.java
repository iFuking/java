package cn.com.jtang.healthcloud.dao;

import cn.com.jtang.healthcloud.pojo.Relation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;
import cn.com.jtang.healthcloud.pojo.Report;

public class ManageReport {

    private static SessionFactory factory;
    public ManageReport() {
        try {
        	factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // INSERT a record into table or UPDATE a existing record in table
    public boolean addOrUpdateReport(String openId, Relation rel, Report rep) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            String hql = "FROM Relation rel WHERE rel.openId = :openId";
            Query query = session.createQuery(hql);
            query.setParameter("openId", openId);
            List relationList = query.list();
            if (relationList.size() == 0) {
                session.save(rel);
                session.save(rep);
            } else {
            	Relation relation = (Relation) relationList.get(0);
            	relation.setOpenId(openId).setReportId(rel.getReportId());
            	relation.setDeviceId(rel.getDeviceId()).setTimestamp(rel.getTimestamp());
                session.update(relation);
                
                hql = "FROM Report rep WHERE rep.reportId = :reportId";
                query = session.createQuery(hql);
                query.setParameter("reportId", rep.getReportId());
                Report report = (Report) query.list().get(0);
                report.setReportId(rep.getReportId()).setTimestamp(rep.getTimestamp());
                if (rep.getHeight() != null) report.setHeight(rep.getHeight());
                if (rep.getWeight() != null) report.setWeight(rep.getWeight());
                if (rep.getBodyFatRate() != null) report.setBodyFatRate(rep.getBodyFatRate());
                if (rep.getSpo2h() != null) report.setSpo2h(rep.getSpo2h());
                if (rep.getSystolicPressure() != null) report.setSystolicPressure(rep.getSystolicPressure());
                if (rep.getDiastolicPressure() != null) report.setDiastolicPressure(rep.getDiastolicPressure());
                if (rep.getBeatsPerMinute() != null) report.setBeatsPerMinute(rep.getBeatsPerMinute());
                if (rep.getViscera() != null) report.setViscera(rep.getViscera());
                if (rep.getSpine() != null) report.setSpine(rep.getSpine());
                if (rep.getDigestion() != null) report.setDigestion(rep.getDigestion());
                if (rep.getUrinary() != null) report.setUrinary(rep.getUrinary());
                if (rep.getAdvice() != null) report.setAdvice(rep.getAdvice());
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
    
    // LIST all the report records and RETURN
    public List<Report> listWesternMedicineReport(String openId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Report> reportList = new ArrayList();
        try {
            tx = session.beginTransaction();
            String hql = "FROM Relation rel WHERE rel.openId = :openId";
            Query query = session.createQuery(hql);
            query.setParameter("openId", openId);
            List<Relation> relationList = query.list();
            
            for (int i = 0; i < relationList.size(); ++i) {
            	String reportId = relationList.get(i).getReportId();
            	hql = "FROM Report rep WHERE rep.reportId = :reportId";
            	query = session.createQuery(hql);
            	query.setParameter("reportId", reportId);
            	reportList.add((Report) query.list().get(0));
            }
            if (relationList.size() == 0) System.out.println("No such Report record, openId='"+openId+"'");
            else System.out.println("LIST "+relationList.size()+" Report record(s), openId='"+openId+"'");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reportList;
    }
    
    // SELECT a report record
    public Report selectWesternMedicineReport(String reportId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Report> report = null;
        try {
            tx = session.beginTransaction();
            String hql = "FROM Report rep WHERE rep.reportId = :reportId";
            Query query = session.createQuery(hql);
            query.setParameter("reportId", reportId);
            report = query.list();
            if (report.size() == 0) System.out.println("No such Report record, reportId='"+reportId+"'");
            else System.out.println("SELECT a Report record, reportId='"+reportId+"'");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return report.size()==0 ? null : report.get(0);
    }
}
