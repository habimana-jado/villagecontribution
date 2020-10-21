/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Dao.UserDao;
import Domain.User;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Student
 */
@ManagedBean
@SessionScoped
public class UserModel {

    private User user = new User();

    private User userDetails = new User();

    private UserDao userDao = new UserDao();

    private List<User> users;

    private String username = new String();

    private String password = new String();

    private String userdetails = new String();

    private String sid = new String();

    private String sectid = new String();

    private User u = new User();

    @PostConstruct
    public void init() {
//        users = userDao.FindAll(User.class);
    }

    public String login() throws IOException, Exception {
        findUser();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (user != null) {

            switch (user.getAccess()) {
                case "Admin":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/pages/admin/dashboard.xhtml");
                    return "faces/pages/admin/dashboard.xhtml?faces-redirect=true";
                case "Supervisor":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/pages/supervisor/dashboard.xhtml");
                    return "faces/pages/supervisor/dashboard.xhtml?faces-redirect=true";
                case "Resident":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/pages/resident/dashboard.xhtml");
                    return "faces/pages/resident/dashboard.xhtml?faces-redirect=true";
                case "Guard":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/pages/guard/pay.xhtml");
                    return "faces/pages/guard/pay.xhtml?faces-redirect=true";
                case "Blocked":
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Account is Blocked"));
                    ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
                    return "faces/index.xhtml";
                default:
                    user = null;

                    ec.redirect(ec.getRequestContextPath() + "/faces/pages/index.xhtml");

                    return "/VillageContributionApp/faces/pages/index.xhtml";
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong Password Or Username"));
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
            return "faces/index.xhtml";
        }

    }

    public String logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = null;
        return "/faces/index.xhtml?faces-redirect=true";
    }

    public void findUser() throws Exception {
        List<User> usersLogin = new UserDao().loginencrypt(username, password);

        if (!usersLogin.isEmpty()) {
            for (User u : usersLogin) {
                user = u;
            }
        } else {
            user = null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(String userdetails) {
        this.userdetails = userdetails;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSectid() {
        return sectid;
    }

    public void setSectid(String sectid) {
        this.sectid = sectid;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

}
