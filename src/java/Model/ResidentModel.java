/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Common.FileUpload;
import Dao.ResidentDao;
import Dao.TransactionDao;
import Dao.UserDao;
import Domain.Resident;
import Domain.Transaction;
import Domain.User;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author student
 */
@ManagedBean
@SessionScoped
public class ResidentModel {

    private Resident resident = new Resident();

    private Resident residentDetails = new Resident();

    ResidentDao residentDao = new ResidentDao();

    private String searchKey = new String();

    private List<Resident> residents;

    private List<Resident> residentelle;

    private String cid = new String();

    private User u = new User();

    private String type;

    private long empId;

    private long servId;

    private String password;

    private Resident searchres = new Resident();

    private Resident sup = new Resident();

    private List<String> choosenImage = new ArrayList<>();

    private List<String> profileImage = new ArrayList<>();

    private List<Transaction> transactions = new ArrayList<>();

    private double amount;

    private int month;

    private double amt = 0.0;

    private double inUsd = 5000;

    private double x = 0.0;

    private double payamt = 0.0;
    @PostConstruct
    public void init() {
        nameInit();
        transactionInit();
    }

    public void calculate() throws DocumentException, BadElementException, Exception {
        User y = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        
        double z = 0.0;
        
        if(y.getResident().getUbudehe() == 4){
            z = 1;
        }else if(y.getResident().getUbudehe() == 3){
            z = 0.75;
        }else if(y.getResident().getUbudehe() == 2){
            z = 0.5;
        }else if(y.getResident().getUbudehe() == 1){
            z = 0.25;
        }
        amt = (y.getResident().getSupervisor().getAdmin().getContribution() * z) * month;
        inUsd = amt / 900;
//        System.out.println(payamt);
        
        
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("You are to pay: " + amt + ""));
//        createContribution();
//        System.out.println(inUsd);
//        return "contribution.xhtml?faces-redirect=true";
    }

    public String change() {
        return "pay.xhtml?faces-redirect=true";
    }

    public void createContribution() throws IOException, DocumentException, BadElementException, Exception {
        String msg = "";
        
        User a = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Resident r = a.getResident();

        Transaction t = new Transaction();

        SimpleDateFormat sdfm = new SimpleDateFormat("MM");
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");

        String year = sdfy.format(new Date());
        
        User y = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        
        double z = 0.0;
        
        if(y.getResident().getUbudehe() == 4){
            z = 1;
        }else if(y.getResident().getUbudehe() == 3){
            z = 0.75;
        }else if(y.getResident().getUbudehe() == 2){
            z = 0.5;
        }else if(y.getResident().getUbudehe() == 1){
            z = 0.25;
        }
         payamt = (y.getResident().getSupervisor().getAdmin().getContribution() * z);
        System.out.println(payamt);
        for (Transaction x : new TransactionDao().FindAll(Transaction.class)) {
            if (x.getResident().getId().matches(r.getId())) {
                if (x.getYear().matches(year)) {
                    if ((x.getJanuary() == -payamt) && (this.month == 1)) {
                        x.setJanuary(payamt);
                        msg = "You have paid for month January";
                        new TransactionDao().Update(x);
                        
                    } else if ((x.getJanuary() == -payamt) && (this.month == 2)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February";
               
                    } else if ((x.getJanuary() == -payamt) && (this.month == 3)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March";
                       
                    } else if ((x.getJanuary() == -payamt) && (this.month == 4)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April";
                       
                    } else if ((x.getJanuary() == -payamt) && (this.month == 5)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        new TransactionDao().Update(x);

                        msg = "You have paid for month January, February, March, April, May";
              
                    } else if ((x.getJanuary() == -payamt) && (this.month == 6)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June";
                     
                    } else if ((x.getJanuary() == -payamt) && (this.month == 6)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June";
                      
                    } else if ((x.getJanuary() == -payamt) && (this.month == 7)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June, July";
                     
                    } else if ((x.getJanuary() == -payamt) && (this.month == 8)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June, July, August";
                      
                    } else if ((x.getJanuary() == -payamt) && (this.month == 9)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June, July, August, September";
                        
                    } else if ((x.getJanuary() == -payamt) && (this.month == 10)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June, July, August, September, October";
                        
                    } else if ((x.getJanuary() == -payamt) && (this.month == 11)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                     
                    } else if ((x.getJanuary() == -payamt) && (this.month == 12)) {
                        x.setJanuary(payamt);
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month January, February, March, April, May, June, July, August, September, October, November, December";
                     
                    } else if ((x.getFebruary() == -payamt) && (this.month == 1)) {
                        x.setFebruary(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month February";
                       
                    } else if ((x.getFebruary() == -payamt) && (this.month == 2)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month February, March";
                       
                    } else if ((x.getFebruary() == -payamt) && (this.month == 3)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  February, March, April";
                     
                    } else if ((x.getFebruary() == -payamt) && (this.month == 4)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        new TransactionDao().Update(x);

                        msg = "You have paid for month February, March, April, May";
                       
                    } else if ((x.getFebruary() == -payamt) && (this.month == 5)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        
                    } else if ((x.getFebruary() == -payamt) && (this.month == 6)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month February, March, April, May, June, July";
                     
                    } else if ((x.getFebruary() == -payamt) && (this.month == 7)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month February, March, April, May, June, July, August";
                       
                    } else if ((x.getFebruary() == -payamt) && (this.month == 8)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month February, March, April, May, June, July, August, September";
                        
                    } else if ((x.getFebruary() == -payamt) && (this.month == 9)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  February, March, April, May, June, July, August, September, October";
                       
                    } else if ((x.getFebruary() == -payamt) && (this.month == 10)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  February, March, April, May, June, July, August, September, October, November";
                     
                    } else if ((x.getFebruary() == -payamt) && (this.month == 11)) {
                        x.setFebruary(payamt);
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  February, March, April, May, June, July, August, September, October, November, December";
                  
                    } else if ((x.getMarch() == -payamt) && (this.month == 1)) {
                        x.setMarch(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month March";
                        
                    } else if ((x.getMarch() == -payamt) && (this.month == 2)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  March, April";
                      
                    } else if ((x.getMarch() == -payamt) && (this.month == 3)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        new TransactionDao().Update(x);

                        msg = "You have paid for month March, April, May";
                     
                    } else if ((x.getMarch() == -payamt) && (this.month == 4)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month March, April, May, June";
                 
                    } else if ((x.getMarch() == -payamt) && (this.month == 5)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month March, April, May, June, July";
                        
                    } else if ((x.getMarch() == -payamt) && (this.month == 6)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                       
                    } else if ((x.getMarch() == -payamt) && (this.month == 7)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month March, April, May, June, July, August, September";
                       
                    } else if ((x.getMarch() == -payamt) && (this.month == 8)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  March, April, May, June, July, August, September, October";
                   
                    } else if ((x.getMarch() == -payamt) && (this.month == 9)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  March, April, May, June, July, August, September, October, November";
                        
                    } else if ((x.getMarch() == -payamt) && (this.month == 10)) {
                        x.setMarch(payamt);
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  March, April, May, June, July, August, September, October, November, December";
                      
                    } else if ((x.getApril() == -payamt) && (this.month == 1)) {
                        x.setApril(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month April";
                     
                    } else if ((x.getApril() == -payamt) && (this.month == 2)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        new TransactionDao().Update(x);

                        msg = "You have paid for month April, May";
                        
                    } else if ((x.getApril() == -payamt) && (this.month == 3)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month April, May, June";
                        
                    } else if ((x.getApril() == -payamt) && (this.month == 4)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month April, May, June, July";
                      
                    } else if ((x.getApril() == -payamt) && (this.month == 5)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month April, May, June, July, August";
                 
                    } else if ((x.getApril() == -payamt) && (this.month == 6)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month April, May, June, July, August, September";
                        
                    } else if ((x.getApril() == -payamt) && (this.month == 7)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  April, May, June, July, August, September, October";
                      
                    } else if ((x.getApril() == -payamt) && (this.month == 8)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  April, May, June, July, August, September, October, November";
                        
                    } else if ((x.getApril() == -payamt) && (this.month == 9)) {
                        x.setApril(payamt);
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  April, May, June, July, August, September, October, November, December";
                      
                    } else if ((x.getMay() == -payamt) && (this.month == 1)) {
                        x.setMay(payamt);
                        new TransactionDao().Update(x);

                        msg = "You have paid for month May";
                       
                    } else if ((x.getMay() == -payamt) && (this.month == 2)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month May, June";
                        
                    } else if ((x.getMay() == -payamt) && (this.month == 3)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month May, June, July";
                        
                    } else if ((x.getMay() == -payamt) && (this.month == 4)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month May, June, July, August";
                        
                    } else if ((x.getMay() == -payamt) && (this.month == 5)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month May, June, July, August, September";
                        
                    } else if ((x.getMay() == -payamt) && (this.month == 6)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  May, June, July, August, September, October";
                       
                    } else if ((x.getMay() == -payamt) && (this.month == 7)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  May, June, July, August, September, October, November";
                        
                    } else if ((x.getMay() == -payamt) && (this.month == 8)) {
                        x.setMay(payamt);
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  May, June, July, August, September, October, November, December";
                        
                        
                    } else if ((x.getJune() == -payamt) && (this.month == 1)) {
                        x.setJune(payamt);
                        new TransactionDao().Update(x);
                       
                    } else if ((x.getJune() == -payamt) && (this.month == 2)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month June, July";
                       
                    } else if ((x.getJune() == -payamt) && (this.month == 3)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month June, July, August";
                        
                    } else if ((x.getJune() == -payamt) && (this.month == 4)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month June, July, August, September";
                       
                    } else if ((x.getJune() == -payamt) && (this.month == 5)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  June, July, August, September, October";
                        
                    } else if ((x.getJune() == -payamt) && (this.month == 6)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  June, July, August, September, October, November";
                       
                    } else if ((x.getJune() == -payamt) && (this.month == 7)) {
                        x.setJune(payamt);
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  June, July, August, September, October, November, December";
                      
                    } else if ((x.getJuly() == -payamt) && (this.month == 1)) {
                        x.setJuly(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month July";
                        
                    } else if ((x.getJuly() == -payamt) && (this.month == 2)) {
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month July, August";
                       
                    } else if ((x.getJuly() == -payamt) && (this.month == 3)) {
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month July, August, September";
                      
                    } else if ((x.getJuly() == -payamt) && (this.month == 4)) {
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  July, August, September, October";
                      
                    } else if ((x.getJuly() == -payamt) && (this.month == 5)) {
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month  July, August, September, October, November";
                        
                    } else if ((x.getJuly() == -payamt) && (this.month == 6)) {
                        x.setJuly(payamt);
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month July, August, September, October, November, December";
                       
                    } else if ((x.getAugust() == -payamt) && (this.month == 1)) {
                        x.setAugust(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month August";
                        
                    } else if ((x.getAugust() == -payamt) && (this.month == 2)) {
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month August, September";
                     
                    } else if ((x.getAugust() == -payamt) && (this.month == 3)) {
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month August, September, October";
                        
                    } else if ((x.getAugust() == -payamt) && (this.month == 4)) {
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month August, September, October, November";
                       
                    } else if ((x.getAugust() == -payamt) && (this.month == 5)) {
                        x.setAugust(payamt);
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month August, September, October, November, December";
                        
                    } else if ((x.getSeptember() == -payamt) && (this.month == 1)) {
                        x.setSeptember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month September";
                       
                    } else if ((x.getSeptember() == -payamt) && (this.month == 2)) {
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month September, October";
                        
                    } else if ((x.getSeptember() == -payamt) && (this.month == 3)) {
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month September, October, November";
                        
                    } else if ((x.getSeptember() == -payamt) && (this.month == 4)) {
                        x.setSeptember(payamt);
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month September, October, November, December";
                      
                    } else if ((x.getOctober() == -payamt) && (this.month == 1)) {
                        x.setOctober(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month October";
                      
                    } else if ((x.getOctober() == -payamt) && (this.month == 2)) {
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month October, November";
                       
                    } else if ((x.getOctober() == -payamt) && (this.month == 3)) {
                        x.setOctober(payamt);
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month October, November, December";
                      
                    } else if ((x.getNovember() == -payamt) && (this.month == 1)) {
                        x.setNovember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month November";
                        
                    } else if ((x.getNovember() == -payamt) && (this.month == 2)) {
                        x.setNovember(payamt);
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                        msg = "You have paid for month November, December";
                      
                    } else if ((x.getDecember() == -payamt) && (this.month == 1)) {
                        x.setDecember(payamt);
                        new TransactionDao().Update(x);
                      
                    }
                    new TransactionDao().Update(x);
                    
                    generateWomenPdf();
                } else {

                    if (x.getJanuary() == -payamt) {
                        x.setJanuary(payamt);
                        msg = "You have paid for month January";
                        new TransactionDao().Update(x);
                    } else if (x.getFebruary() == -payamt) {
                        x.setFebruary(payamt);
                        msg = "You have paid for month February";
                        new TransactionDao().Update(x);
                    } else if (x.getMarch() == -payamt) {
                        x.setMarch(payamt);
                        msg = "You have paid for month March";
                        new TransactionDao().Update(x);
                    } else if (x.getApril() == -payamt) {
                        x.setApril(payamt);
                        msg = "You have paid for month April";
                        new TransactionDao().Update(x);
                    } else if (x.getMay() == -payamt) {
                        x.setMay(payamt);
                        msg = "You have paid for month May";
                        new TransactionDao().Update(x);
                    } else if (x.getJune() == -payamt) {
                        x.setJune(payamt);
                        msg = "You have paid for month June";
                        new TransactionDao().Update(x);
                    } else if (x.getJuly() == -payamt) {
                        x.setJuly(payamt);
                        msg = "You have paid for month July";
                        new TransactionDao().Update(x);
                    } else if (x.getAugust() == -payamt) {
                        x.setAugust(payamt);
                        msg = "You have paid for month August";
                        new TransactionDao().Update(x);
                    } else if (x.getSeptember() == -payamt) {
                        x.setSeptember(payamt);
                        msg = "You have paid for month September";
                        new TransactionDao().Update(x);
                    } else if (x.getOctober() == -payamt) {
                        x.setOctober(payamt);
                        msg = "You have paid for month October";
                        new TransactionDao().Update(x);
                    } else if (x.getNovember() == -payamt) {
                        x.setNovember(payamt);
                        msg = "You have paid for month November";
                        new TransactionDao().Update(x);
                    } else if (x.getDecember() == -payamt) {
                        x.setDecember(payamt);
                        msg = "You have paid for month December";
                        new TransactionDao().Update(x);
                    } else {
                        t.setJanuary(-payamt);
                        t.setFebruary(-payamt);
                        t.setMarch(-payamt);
                        t.setApril(-payamt);
                        t.setMay(-payamt);
                        t.setJune(-payamt);
                        t.setJuly(-payamt);
                        t.setAugust(-payamt);
                        t.setSeptember(-payamt);
                        t.setOctober(-payamt);
                        t.setNovember(-payamt);
                        t.setDecember(-payamt);
                        t.setYear(year);
                        t.setResident(r);
                        new TransactionDao().register(t);

                        t.setJanuary(payamt);
                        msg = "You have paid for month January";

                        new TransactionDao().Update(t);
                        generateWomenPdf();
                    }
                }
            }
        }
        transactionInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage(msg));
    }
    public double inUsd() {
        if ((sup.getUbudehe() + "").matches(4 + "")) {
            amt = amt * month;
            x = (amt * month) / 900;

        } else if ((sup.getUbudehe() + "").matches(3 + "")) {
            amt = 1500 * month;
            x = (1500 * month) / 900;
        } else if ((sup.getUbudehe() + "").matches(2 + "")) {
            amt = 1000 * month;
            x = (1000 * month) / 900;
        } else if ((sup.getUbudehe() + "").matches(1 + "")) {
            amt = 500 * month;
            x = (500 * month) / 900;
        }
        System.out.println(amt + "  " + x);
        return x;
    }

    public void nameInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        sup = x.getResident();
    }

    public String navigateupdate(Resident x) {
        searchres = x;
        return "updateresident.xhtml?faces-redirect=true";
    }

    public String navigate() {
        return "editprofile.xhtml?faces-redirect=true";
    }

    public void generateWomenPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(20, 20, 580, 500);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
//        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\layout");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\layout\\layout\\img\\womenLOGO2.png";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(50, 50);
//        image.setAlignment(Element.ALIGN_LEFT);
        Paragraph title = new Paragraph();
        //BEGIN page
//        title.add(image);
        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 9, Font.ITALIC, new Color(90, 255, 20));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Resident Receipt Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        Paragraph p1 = new Paragraph("Resident Name: " + sup.getName()+ "\n", colorFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            document.add(p1);
            document.add(new Paragraph("\n"));

            Paragraph p2 = new Paragraph("Total Month Paid: " + month + "\n", colorFont);
            p2.setAlignment(Element.ALIGN_LEFT);
            document.add(p2);
            document.add(new Paragraph("\n"));

            Paragraph p3 = new Paragraph("Total Amount Paid: " + amt + " \n", colorFont);
            p3.setAlignment(Element.ALIGN_LEFT);
            document.add(p3);
            document.add(new Paragraph("\n"));

            Paragraph p4 = new Paragraph("Pay Date: " + new SimpleDateFormat("dd/MMM/yyyy").format(new Date()) + "\n\n", colorFont);
            p4.setAlignment(Element.ALIGN_LEFT);
            document.add(p4);
            document.add(new Paragraph("\n"));

            Paragraph p5 = new Paragraph("This Bill is valid only it bears a stamp\n", colorFont);
            p5.setAlignment(Element.ALIGN_CENTER);
            document.add(p5);
            document.add(new Paragraph("\n"));
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()), font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    
    private void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) throws IOException {
        externalContext.responseReset();
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        externalContext.setResponseContentLength(baos.size());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
    }

    public void resign() {
        sup.setStatus("Moved");
        new ResidentDao().Update(sup);
        User u = sup.getUser();
        u.setAccess("Blocked");
        new UserDao().Update(u);
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Account Removed"));
    }

    public void Upload(FileUploadEvent event) {
        choosenImage.add(new FileUpload().Upload(event, "C:\\Users\\Issa\\Desktop\\VillageContributionApp\\web\\uploads\\"));
    }

    public void Uploadpic(FileUploadEvent event) {
        profileImage.add(new FileUpload().Upload(event, "C:\\Users\\Issa\\Desktop\\VillageContributionApp\\web\\uploads\\profile\\"));
    }

    public void updateResident() {
        if (profileImage.isEmpty()) {
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Upload Resident's Profile Picture"));
        } else {
            for (String z : profileImage) {
                sup.setImage(z);
            }
            residentDao.Update(sup);
            residentDetails = new Resident();

            residents = residentDao.FindAll(Resident.class);
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Profile updated"));
        }
    }

    public void deleteResident() {
        residentDao.Delete(resident);
        residents = residentDao.FindAll(Resident.class);

        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Resident Deleted", ""));

    }

    public void transactionInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Resident a = x.getResident();
        transactions = new TransactionDao().findByResident(a);
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Resident getResidentDetails() {
        return residentDetails;
    }

    public void setResidentDetails(Resident residentDetails) {
        this.residentDetails = residentDetails;
    }

    public ResidentDao getResidentDao() {
        return residentDao;
    }

    public void setResidentDao(ResidentDao residentDao) {
        this.residentDao = residentDao;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public List<Resident> getResidentelle() {
        return residentelle;
    }

    public void setResidentelle(List<Resident> residentelle) {
        this.residentelle = residentelle;
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

    public Resident getSearchres() {
        return searchres;
    }

    public void setSearchres(Resident searchres) {
        this.searchres = searchres;
    }

    public Resident getSup() {
        return sup;
    }

    public void setSup(Resident sup) {
        this.sup = sup;
    }

    public List<String> getChoosenImage() {
        return choosenImage;
    }

    public void setChoosenImage(List<String> choosenImage) {
        this.choosenImage = choosenImage;
    }

    public List<String> getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(List<String> profileImage) {
        this.profileImage = profileImage;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getInUsd() {
        return inUsd;
    }

    public void setInUsd(double inUsd) {
        this.inUsd = inUsd;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getPayamt() {
        return payamt;
    }

    public void setPayamt(double payamt) {
        this.payamt = payamt;
    }

}
