/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Common.PassCode;
import Dao.AdminDao;
import Dao.UserDao;
import Domain.Admin;
import Domain.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author student
 */
@ManagedBean
@SessionScoped
public class AdminModel {
    private Admin admin = new Admin();

    private Admin adminDetails = new Admin();

    AdminDao adminDao = new AdminDao();

    private String searchKey = new String();

    private List<Admin> admins;
    
    private List<Admin> adminelle;
    
    private String cid = new String();

    private User u= new User();
    
    private String type;
    
    private long empId;
    
    private long servId;
    
    private String password;
    
    @PostConstruct
    public void init() {
        admins = new AdminDao().FindAll(Admin.class);
    }

    public String createAdmin() throws Exception {
        String pass = new PassCode().encrypt(password);
        u.setPassword(pass);
        
        adminDao = new AdminDao();
        adminDao.register(admin);
        u.setAccess("Admin");
        u.setStatus("Active");
        u.setAdmin(admin);
        new UserDao().register(u);
        u = new User();
        admin = new Admin();

        admins = adminDao.FindAll(Admin.class);
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Admin Registered"));
        return "index.xhtml?faces-redirect=true";
    }

    public void updateAdmin() {
        adminDao.Update(adminDetails);
        adminDetails = new Admin();

        admins = adminDao.FindAll(Admin.class);
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Admin updated"));
    }

    public void deleteAdmin() {
        adminDao.Delete(admin);
        admins = adminDao.FindAll(Admin.class);

        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin Deleted", ""));

    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdminDetails() {
        return adminDetails;
    }

    public void setAdminDetails(Admin adminDetails) {
        this.adminDetails = adminDetails;
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Admin> getAdminelle() {
        return adminelle;
    }

    public void setAdminelle(List<Admin> adminelle) {
        this.adminelle = adminelle;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public long getServId() {
        return servId;
    }

    public void setServId(long servId) {
        this.servId = servId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
