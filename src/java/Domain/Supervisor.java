/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author student
 */
@Entity
public class Supervisor implements Serializable {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String phone;
    private String email;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date hireDate;
    private double salary;
    private String image;                              //Blocked, Active
    private String status;
    
    @OneToOne(mappedBy = "supervisor", fetch = FetchType.EAGER)
    private User user;
    
    @ManyToOne
    private Admin admin;
    
    @OneToMany(mappedBy = "supervisor", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Resident> resident;
    
    @OneToMany(mappedBy = "supervisor", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Guard> guard;
    
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

   
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Resident> getResident() {
        return resident;
    }

    public void setResident(List<Resident> resident) {
        this.resident = resident;
    }

    public List<Guard> getGuard() {
        return guard;
    }

    public void setGuard(List<Guard> guard) {
        this.guard = guard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
