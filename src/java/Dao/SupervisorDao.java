/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Domain.Admin;
import Domain.Resident;
import Domain.Supervisor;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author student
 */
public class SupervisorDao extends GenericDao<Supervisor>{
    public List<Supervisor> findByAdmin(Admin x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Supervisor a where a.admin=:v");
        q.setParameter("v", x);
        List<Supervisor> list = q.list();
        s.close();
        return list;
    }
        
    public Long supervisorcount(Admin x, String z){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select count(id) from Supervisor a where a.admin=:v and a.status = :z");
        q.setParameter("v", x);
        q.setParameter("z", z);
        Long g = (Long) q.uniqueResult();
        List<Resident> list = q.list();
        s.close();
        return g;       
                    
    }
}
