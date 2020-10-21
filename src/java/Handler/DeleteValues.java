/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Dao.GuardDao;
import Dao.ResidentDao;
import Dao.SupervisorDao;
import Dao.UserDao;
import Domain.Guard;
import Domain.Resident;
import Domain.Supervisor;
import Domain.User;

/**
 *
 * @author student
 */
public class DeleteValues {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Transaction t = new TransactionDao().FindOne(Transaction.class, "00b89991-02a8-4c0b-953e-196f076564b6");
//        new TransactionDao().Delete(t);

//            for(Supervisor s: new SupervisorDao().FindAll(Supervisor.class)){
//                s.setStatus("Available");
//                new SupervisorDao().Update(s);
//            }
//            new SupervisorDao().Update(new SupervisorDao().FindOne(Supervisor.class, "df6512a0-e7b5-4d8b-9678-9ea9d3b4a7b7").setStatus("Available"));
            
//            Supervisor x = new SupervisorDao().FindOne(Supervisor.class, "df6512a0-e7b5-4d8b-9678-9ea9d3b4a7b7");
//            x.setStatus("Available");
//            new SupervisorDao().Update(x);
            
//            User g = new UserDao().FindOne(User.class, "cab3c8e6-1e8c-4fe2-a1a2-b7a8fc65ea91");
//            new UserDao().Delete(g);


            for(Resident g: new ResidentDao().FindAll(Resident.class)){
                g.setPhone("+250727172852");
                new ResidentDao().Update(g);
            }
    }
    
}
