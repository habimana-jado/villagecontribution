/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Common.PassCode;
import Domain.Resident;
import Domain.Supervisor;
import Domain.User;
import static com.mchange.v2.c3p0.impl.C3P0Defaults.password;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author student
 */
public class UserDao extends GenericDao<User>{
    public List<User>login(String u,String password){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("select a from User a where a.username=:v and a.password=:p");
        q.setParameter("v", u);
        q.setParameter("p", password);
        List<User> l=q.list();
        return l;
    } 
    public List<User> findByResident(Resident u){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("select a from User a where a.resident=:v ");
        q.setParameter("v", u);
        List<User> l=q.list();
        return l;
    } 
    public List<User> findBySupervisor(Supervisor u){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("select a from User a where a.supervisor=:v ");
        q.setParameter("v", u);
        List<User> l=q.list();
        return l;
    } 
    public List<User> loginencrypt(String u, String pass) throws Exception {

        Session s = HibernateUtil.getSessionFactory().openSession();
        List<User> list = new ArrayList<>();

        List<User> users = new UserDao().FindAll(User.class);
        String z = "";
        for (User us : users) {
            if (us.getUsername().matches(u)) {
                if ((new PassCode().decrypt(us.getPassword())).matches(pass)) {
                    list.add(us);
                }
            }

        }

        s.close();
        return list;

    }
}
