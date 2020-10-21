/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author student
 */
@Entity
public class Resident implements Serializable {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String email;
    private int ubudehe;                //1,2,3,4
    private String phone;
    private String image;             
    private String status;              //Available, Moved
    
    
    @OneToMany(mappedBy = "resident", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Transaction> transaction;
    
    @OneToOne(mappedBy = "resident", fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    private Supervisor supervisor;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUbudehe() {
        return ubudehe;
    }

    public void setUbudehe(int ubudehe) {
        this.ubudehe = ubudehe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
