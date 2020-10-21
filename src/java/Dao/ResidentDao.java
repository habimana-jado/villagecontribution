/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Domain.Admin;
import Domain.Resident;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author student
 */
public class ResidentDao extends GenericDao<Resident>{
    public List<Resident> findByAdmin(Admin x, String z) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Resident a where a.supervisor.admin=:v and a.status = :z");
        q.setParameter("v", x);
        q.setParameter("z", z);
        List<Resident> list = q.list();
        s.close();
        return list;
    }
    public Long residentcount(Admin x, String z){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select count(id) from Resident a where a.supervisor.admin=:v and a.status = :z");
        q.setParameter("v", x);
        q.setParameter("z", z);
        Long g = (Long) q.uniqueResult();
        List<Resident> list = q.list();
        s.close();
        return g;
          
                    
    }
}
