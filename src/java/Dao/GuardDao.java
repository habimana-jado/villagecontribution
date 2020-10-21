/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Domain.Admin;
import Domain.Guard;
import Domain.Resident;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author student
 */
public class GuardDao extends GenericDao<Guard>{
    public List<Guard> findByAdmin(Admin x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Guard a where a.supervisor.admin=:v");
        q.setParameter("v", x);
        List<Guard> list = q.list();
        s.close();
        return list;
    }
    public double totalsalary(Admin g){
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Double cont = (Double) s.createQuery("select sum(salary) from Guard a where a.supervisor.admin = ? ").setParameter(0, g).uniqueResult();
        System.out.println("Amount = "+cont);
        s.getTransaction().commit();
        s.close();
        return cont;
    }
    
    
    public Long guardcount(Admin x, String z){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select count(id) from Guard a where a.supervisor.admin=:v and a.status = :z");
        q.setParameter("v", x);
        q.setParameter("z", z);
        Long g = (Long) q.uniqueResult();
        List<Resident> list = q.list();
        s.close();
        return g;      
                    
    }
}
