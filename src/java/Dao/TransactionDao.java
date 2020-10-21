/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Domain.Admin;
import Domain.Resident;
import Domain.Transaction;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author student
 */
public class TransactionDao extends GenericDao<Transaction>{
    public double transactionByMonth(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Double cont = (Double) s.createQuery("select sum(january) from Transaction a ").uniqueResult();
        System.out.println("Amount = "+cont);
        s.getTransaction().commit();
        s.close();
        return cont;
    }
    public List<Transaction> findByResident(Resident x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Transaction a where a.resident=:v");
        q.setParameter("v", x);
        List<Transaction> list = q.list();
        s.close();
        return list;
    }
    public List<Transaction> findByAdmin(Admin x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Transaction a where a.resident.supervisor.admin=:v");
        q.setParameter("v", x);
        List<Transaction> list = q.list();
        s.close();
        return list;
    }
    
}
