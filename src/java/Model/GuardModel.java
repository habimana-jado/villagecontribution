/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Common.FileUpload;
import Common.PassCode;
import Dao.GuardDao;
import Dao.ResidentDao;
import Dao.SupervisorDao;
import Dao.TransactionDao;
import Dao.UserDao;
import Domain.Admin;
import Domain.Guard;
import Domain.Pay;
import Domain.Resident;
import Domain.Supervisor;
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
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
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
public class GuardModel {

    private Guard guard = new Guard();
    
    private Guard chosenGuard = new Guard();

    private Guard guardDetails = new Guard();

    GuardDao guardDao = new GuardDao();

    private String searchKey = new String();

    private List<Guard> guards;

    private List<Guard> guardelle;

    private Supervisor sup;

    private String cid = new String();

    private User u = new User();

    private String type;

    private long empId;

    private long servId;

    private String password;

    private Guard searchguard = new Guard();

    private List<String> choosenImage = new ArrayList<>();

    private Resident resident = new Resident();

    private Resident residentDetails = new Resident();

    ResidentDao residentDao = new ResidentDao();

    private List<Resident> residents;

    private List<Transaction> transactions = new ArrayList<>();

    private List<Pay> payments;

    private String month = new SimpleDateFormat("MM").format(new Date());

    private Double monthCont = 0.0;

    private Double salarytot;

    private int ubudehe;

    private long rescount;

    private long guardcount;

    private String num = new String();

    private double amt = 0.0;

    private String sms = new String();

    private double salary = 0.0;

    @PostConstruct
    public void init() {
        nameInit();
        guardInit();
        residentInit();
        transactionInit();
    }

    public void sendSms1() {
        final String ACCOUNT_SID
                = "ACf07fe3ed6597eda86f5834c6db7a6478";
        final String AUTH_TOKEN
                = "c7fe2c573c7514105cf9d7632becf5b7";
        int i = 0;
        for (Guard x : guards) {

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String receiver = x.getPhone();
            com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
                    .creator(new PhoneNumber(receiver), // to
                            new PhoneNumber("+16368757037"), // from
                            sms)
                    .create();

            System.out.println(message.getSid());
        }
    }

    public void sendSms() {
        final String ACCOUNT_SID
                = "ACf07fe3ed6597eda86f5834c6db7a6478";
        final String AUTH_TOKEN
                = "c7fe2c573c7514105cf9d7632becf5b7";
        int i = 0;
        for (Guard x : guards) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String receiver = x.getPhone();
            com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
                    .creator(new PhoneNumber(receiver), // to
                            new PhoneNumber("+16368757037"), // from
                            sms)
                    .create();
        }

    }

    public void sendRequestSms() {
        final String ACCOUNT_SID
                = "ACf07fe3ed6597eda86f5834c6db7a6478";
        final String AUTH_TOKEN
                = "c7fe2c573c7514105cf9d7632becf5b7";
        int i = 0;
        residentInit();
        for (Resident x : residents) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String receiver = x.getPhone();
            com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
                    .creator(new PhoneNumber(receiver), // to
                            new PhoneNumber("+16368757037"), // from
                            sms)
                    .create();
        }

    }

    public void sendResidentSms(Resident x) {
        final String ACCOUNT_SID
                = "ACf07fe3ed6597eda86f5834c6db7a6478";
        final String AUTH_TOKEN
                = "c7fe2c573c7514105cf9d7632becf5b7";
        int i = 0;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String receiver = x.getPhone();
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
                .creator(new PhoneNumber(receiver), // to
                        new PhoneNumber("+16368757037"), // from
                        x.getName()+" You are requested to pay the due month payment")
                .create();

    }
    
    public void removeGuard(Guard r) {
        r.setStatus("Moved");
        new GuardDao().Update(r);

        guardInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Guard Removed"));
    }

    public String editGuard(Guard r) {
        chosenGuard = r;
        
        return "editguard.xhtml?faces-redirect=true";
    }


    public void nameInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        sup = x.getSupervisor();
    }

    public String navigateupdate(Guard x) {
        searchguard = x;
        return "updateguard.xhtml?faces-redirect=true";
    }

    public void remove(Resident r) {
        r.setStatus("Moved");
        new ResidentDao().Update(r);

        for (User u : new UserDao().findByResident(r)) {
            u.setAccess("Blocked");
            new UserDao().Update(u);
        }
        residentInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Resident Removed"));
    }

    public String navigate() {
        return "editprofile.xhtml?faces-redirect=true";
    }

    public void guardInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getSupervisor().getAdmin();
        guards = new GuardDao().findByAdmin(a);
        guardcount = new GuardDao().guardcount(a, "Available");
    }

    public void transactionInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getSupervisor().getAdmin();
        transactions = new TransactionDao().findByAdmin(a);
    }

    public void salaryInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getSupervisor().getAdmin();
        salarytot = new GuardDao().totalsalary(a);
    }

    public void residentInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getSupervisor().getAdmin();
        residents = new ResidentDao().findByAdmin(a, "Available");
        rescount = new ResidentDao().residentcount(a, "Available");
    }

    public void createGuard() throws Exception {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Supervisor a = x.getSupervisor();
        guard.setSupervisor(a);
        guard.setStatus("Available");
        guardDao = new GuardDao();
        guardDao.register(guard);
        guard = new Guard();

        guardInit();
        salaryInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Guard Registered"));
    }

    public void calculate() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getSupervisor().getAdmin();
        transactions = new TransactionDao().findByAdmin(a);

        amt = 0.0;
        for (Transaction t : transactions) {
            if (num.matches("January") && t.getJanuary() > 0) {
                amt = amt + t.getJanuary();
            } else if (num.matches("February") && t.getFebruary() > 0) {
                amt = amt + t.getFebruary();
            } else if (num.matches("March") && t.getMarch() > 0) {
                amt = amt + t.getMarch();
            } else if (num.matches("April") && t.getApril() > 0) {
                amt = amt + t.getApril();
            } else if (num.matches("May") && t.getMay() > 0) {
                amt = amt + t.getMay();
            } else if (num.matches("June") && t.getJune() > 0) {
                amt = amt + t.getJune();
            } else if (num.matches("July") && t.getJuly() > 0) {
                amt = amt + t.getJuly();
            } else if (num.matches("August") && t.getAugust() > 0) {
                amt = amt + t.getAugust();
            } else if (num.matches("September") && t.getSeptember() > 0) {
                amt = amt + t.getSeptember();
            } else if (num.matches("October") && t.getOctober() > 0) {
                amt = amt + t.getOctober();
            } else if (num.matches("November") && t.getNovember() > 0) {
                amt = amt + t.getNovember();
            } else if (num.matches("December") && t.getDecember() > 0) {
                amt = amt + t.getDecember();
            }
        }
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
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Contribution Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(14);
        tables.setWidthPercentage(100);
        PdfPCell cell11 = new PdfPCell(new Phrase("Resident Name", font2));
        cell11.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell11);

        PdfPCell cell1 = new PdfPCell(new Phrase("Year", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);
        PdfPCell cell = new PdfPCell(new Phrase("January", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);
        PdfPCell c1 = new PdfPCell(new Phrase("February", font2));
        c1.setBorder(Rectangle.BOTTOM);
        tables.addCell(c1);

        PdfPCell c4 = new PdfPCell(new Phrase("March", font2));
        c4.setBorder(Rectangle.BOTTOM);
        tables.addCell(c4);

        PdfPCell cell2 = new PdfPCell(new Phrase("April", font2));
        cell2.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell2);
        PdfPCell cell3 = new PdfPCell(new Phrase("May", font2));
        cell3.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell3);
        PdfPCell cell4 = new PdfPCell(new Phrase("June", font2));
        cell4.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Phrase("July", font2));
        cell5.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell5);

        PdfPCell cell6 = new PdfPCell(new Phrase("August", font2));
        cell6.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell6);
        PdfPCell cell7 = new PdfPCell(new Phrase("September", font2));
        cell7.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell7);
        PdfPCell cell8 = new PdfPCell(new Phrase("October", font2));
        cell8.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell8);

        PdfPCell cell9 = new PdfPCell(new Phrase("November", font2));
        cell9.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell9);

        PdfPCell cell10 = new PdfPCell(new Phrase("December", font2));
        cell10.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell10);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;

        PdfPCell pdfc9;
        PdfPCell pdfc10;
        PdfPCell pdfc11;
        PdfPCell pdfc12;
        PdfPCell pdfc13;
        PdfPCell pdfc14;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Transaction x : transactions) {

            pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
            pdfc5.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc5);

            pdfc3 = new PdfPCell(new Phrase(x.getYear() + "", font6));
            pdfc3.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc3);

            pdfc4 = new PdfPCell(new Phrase(x.getJanuary() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

            pdfc1 = new PdfPCell(new Phrase(x.getFebruary() + "", font6));
            pdfc1.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc1);

            pdfc2 = new PdfPCell(new Phrase(x.getMarch() + "", font6));
            pdfc2.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc2);

            pdfc6 = new PdfPCell(new Phrase(x.getApril() + "", font6));
            pdfc6.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc6);

            pdfc7 = new PdfPCell(new Phrase(x.getMay() + "", font6));
            pdfc7.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc7);

            pdfc8 = new PdfPCell(new Phrase(x.getJune() + "", font6));
            pdfc8.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc8);

            pdfc9 = new PdfPCell(new Phrase(x.getJuly() + "", font6));
            pdfc9.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc9);

            pdfc10 = new PdfPCell(new Phrase(x.getAugust() + "", font6));
            pdfc10.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc10);

            pdfc11 = new PdfPCell(new Phrase(x.getSeptember() + "", font6));
            pdfc11.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc11);

            pdfc12 = new PdfPCell(new Phrase(x.getOctober() + "", font6));
            pdfc12.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc12);

            pdfc13 = new PdfPCell(new Phrase(x.getNovember() + "", font6));
            pdfc13.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc13);

            pdfc14 = new PdfPCell(new Phrase(x.getDecember() + "", font6));
            pdfc14.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc14);

            i++;
        }
        document.add(tables);
        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()), font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generateSalaryPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
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
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font7 = new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Salary Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell11 = new PdfPCell(new Phrase("Guard Name", font2));
        cell11.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell11);

        PdfPCell cell1 = new PdfPCell(new Phrase("Phone Number", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);
        PdfPCell cell = new PdfPCell(new Phrase("Email", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);
        PdfPCell c1 = new PdfPCell(new Phrase("Salary", font2));
        c1.setBorder(Rectangle.BOTTOM);
        tables.addCell(c1);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;

        PdfPCell pdfc9;
        PdfPCell pdfc10;
        PdfPCell pdfc11;
        PdfPCell pdfc12;
        PdfPCell pdfc13;
        PdfPCell pdfc14;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        salary = 0.0;
        for (Guard x : guards) {

            pdfc5 = new PdfPCell(new Phrase(x.getName(), font6));
            pdfc5.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc5);

            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc3);

            pdfc4 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

            pdfc1 = new PdfPCell(new Phrase(x.getSalary() + "", font6));
            pdfc1.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc1);

            salary = salary + x.getSalary();

            i++;
        }
        document.add(tables);
        Paragraph par1 = new Paragraph("\n\nTotal Salary: " + salary, font7);
        par1.setAlignment(Element.ALIGN_RIGHT);
        document.add(par1);

        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()), font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generatePaidPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
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
        Font font7 = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Paid Resident Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell11 = new PdfPCell(new Phrase("Resident Name", font2));
        cell11.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell11);

        PdfPCell cell2 = new PdfPCell(new Phrase("Contribution", font2));
        cell2.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell2);

        PdfPCell cell1 = new PdfPCell(new Phrase("Ubudehe", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);

        PdfPCell cell = new PdfPCell(new Phrase("Phone Number", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;

        PdfPCell pdfc9;
        PdfPCell pdfc10;
        PdfPCell pdfc11;
        PdfPCell pdfc12;
        PdfPCell pdfc13;
        PdfPCell pdfc14;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        amt = 0.0;
        for (Transaction x : transactions) {
            if (num.matches("January") && x.getJanuary() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJanuary() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJanuary();

            } else if (num.matches("February") && x.getFebruary() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getFebruary() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getFebruary();

            } else if (num.matches("March") && x.getMarch() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMarch() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getMarch();

            } else if (num.matches("April") && x.getApril() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getApril() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getApril();

            } else if (num.matches("May") && x.getMay() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMay() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getMay();

            } else if (num.matches("June") && x.getJune() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJune() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJune();

            } else if (num.matches("July") && x.getJuly() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJuly() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJuly();

            } else if (num.matches("August") && x.getAugust() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getAugust() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getAugust();

            } else if (num.matches("September") && x.getSeptember() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getSeptember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getSeptember();

            } else if (num.matches("October") && x.getOctober() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getOctober() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getOctober();

            } else if (num.matches("November") && x.getNovember() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getNovember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getNovember();

            } else if (num.matches("December") && x.getDecember() > 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getDecember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getDecember();

            }

            i++;
        }
        document.add(tables);
        Paragraph par1 = new Paragraph("\nTotal Amount: " + amt, font7);
        par1.setAlignment(Element.ALIGN_RIGHT);
        document.add(par1);

        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()), font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generateUnpaidPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
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
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font7 = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UnPaid Resident Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell11 = new PdfPCell(new Phrase("Resident Name", font2));
        cell11.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell11);

        PdfPCell cell2 = new PdfPCell(new Phrase("Contribution", font2));
        cell2.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell2);

        PdfPCell cell1 = new PdfPCell(new Phrase("Ubudehe", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);

        PdfPCell cell = new PdfPCell(new Phrase("Phone Number", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;

        PdfPCell pdfc9;
        PdfPCell pdfc10;
        PdfPCell pdfc11;
        PdfPCell pdfc12;
        PdfPCell pdfc13;
        PdfPCell pdfc14;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        amt = 0.0;
        for (Transaction x : transactions) {
            if (num.matches("January") && x.getJanuary() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJanuary() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJanuary();

            } else if (num.matches("February") && x.getFebruary() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getFebruary() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getFebruary();

            } else if (num.matches("March") && x.getMarch() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMarch() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getMarch();

            } else if (num.matches("April") && x.getApril() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getApril() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

            } else if (num.matches("May") && x.getMay() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMay() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getApril();

            } else if (num.matches("June") && x.getJune() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJune() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJune();

            } else if (num.matches("July") && x.getJuly() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJuly() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJuly();

            } else if (num.matches("August") && x.getAugust() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getAugust() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getAugust();

            } else if (num.matches("September") && x.getSeptember() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getSeptember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getSeptember();

            } else if (num.matches("October") && x.getOctober() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getOctober() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getOctober();

            } else if (num.matches("November") && x.getNovember() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getNovember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getNovember();

            } else if (num.matches("December") && x.getDecember() < 0) {

                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getDecember() + "", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone() + "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getDecember();

            }

            i++;
        }
        document.add(tables);
        Paragraph par1 = new Paragraph("\nTotal Amount: " + amt, font7);
        par1.setAlignment(Element.ALIGN_RIGHT);
        document.add(par1);

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

    public void updateGuard() {
        guardDao.Update(searchguard);
        guardDetails = new Guard();

        guardInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Guard updated"));
    }
    
    public void updateGuard1() {
        guardDao.Update(chosenGuard);

        guardInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Guard updated"));
    }

    public void Upload(FileUploadEvent event) {
        choosenImage.add(new FileUpload().Upload(event, "C:\\Users\\Issa\\Desktop\\VillageContributionApp\\web\\uploads\\profile\\"));
    }

    public void deleteGuard() {
        guardDao.Delete(guard);
        guards = guardDao.FindAll(Guard.class);

        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guard Deleted", ""));

    }

    public void createResident() throws Exception {
        if (choosenImage.isEmpty()) {
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Upload Resident's Profile Picture"));
        } else {
            for (String z : choosenImage) {
                resident.setImage(z);
            }
            choosenImage.clear();
            User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
            resident.setSupervisor(x.getSupervisor());
            resident.setUbudehe(ubudehe);
            resident.setStatus("Available");
            residentDao = new ResidentDao();
            residentDao.register(resident);
            String pass = new PassCode().encrypt(password);
            u.setPassword(pass);
            u.setAccess("Resident");
            u.setStatus("Active");
            u.setResident(resident);
            new UserDao().register(u);

            double amt = x.getSupervisor().getAdmin().getContribution();
            Transaction t = new Transaction();
            double z = 0.0;
            if (ubudehe == 1) {
                z = 0.25;
            } else if (ubudehe == 2) {
                z = 0.5;
            } else if (ubudehe == 3) {
                z = 0.75;
            } else if (ubudehe == 4) {
                z = 1;
            }

            SimpleDateFormat sdfm = new SimpleDateFormat("MM");
            SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");

            String month = sdfm.format(new Date());
            String year = sdfy.format(new Date());

            System.out.println(month + "- " + year);

            t.setYear(year);
            if (month.matches("01")) {
                t.setJanuary(-amt * z);
                t.setFebruary(-amt * z);
                t.setMarch(-amt * z);
                t.setApril(-amt * z);
                t.setMay(-amt * z);
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("02")) {
                t.setFebruary(-amt * z);
                t.setMarch(-amt * z);
                t.setApril(-amt * z);
                t.setMay(-amt * z);
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("03")) {
                t.setMarch(-amt * z);
                t.setApril(-amt * z);
                t.setMay(-amt * z);
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("04")) {
                t.setApril(-amt * z);
                t.setMay(-amt * z);
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("05")) {
                t.setMay(-amt * z);
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("06")) {
                t.setJune(-amt * z);
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("07")) {
                t.setJuly(-amt * z);
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("08")) {
                t.setAugust(-amt * z);
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("09")) {
                t.setSeptember(-amt * z);
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("10")) {
                t.setOctober(-amt * z);
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("11")) {
                t.setNovember(-amt * z);
                t.setDecember(-amt * z);
            } else if (month.matches("12")) {
                t.setDecember(-amt * z);
            } else {
                FacesContext ct = FacesContext.getCurrentInstance();
                ct.addMessage(null, new FacesMessage("Unknown month"));
            }
            t.setResident(resident);
            new TransactionDao().register(t);
            u = new User();
            resident = new Resident();

            residentInit();
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Resident Registered"));
        }
    }

    public void updateSupervisor() {
        if (choosenImage.isEmpty()) {
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Upload Supervisor's Profile Picture"));
        } else {
            for (String z : choosenImage) {
                sup.setImage(z);
            }
            new SupervisorDao().Update(sup);

//        guardInit();
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Profile updated"));
        }
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }

    public Guard getGuardDetails() {
        return guardDetails;
    }

    public void setGuardDetails(Guard guardDetails) {
        this.guardDetails = guardDetails;
    }

    public GuardDao getGuardDao() {
        return guardDao;
    }

    public void setGuardDao(GuardDao guardDao) {
        this.guardDao = guardDao;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<Guard> getGuards() {
        return guards;
    }

    public void setGuards(List<Guard> guards) {
        this.guards = guards;
    }

    public List<Guard> getGuardelle() {
        return guardelle;
    }

    public void setGuardelle(List<Guard> guardelle) {
        this.guardelle = guardelle;
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

    public Guard getSearchguard() {
        return searchguard;
    }

    public void setSearchguard(Guard searchguard) {
        this.searchguard = searchguard;
    }

    public Supervisor getSup() {
        return sup;
    }

    public void setSup(Supervisor sup) {
        this.sup = sup;
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

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public List<String> getChoosenImage() {
        return choosenImage;
    }

    public void setChoosenImage(List<String> choosenImage) {
        this.choosenImage = choosenImage;
    }

    public List<Pay> getPayments() {
        return payments;
    }

    public void setPayments(List<Pay> payments) {
        this.payments = payments;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getMonthCont() {
        return monthCont;
    }

    public void setMonthCont(Double monthCont) {
        this.monthCont = monthCont;
    }

    public Double getSalarytot() {
        return salarytot;
    }

    public void setSalarytot(Double salarytot) {
        this.salarytot = salarytot;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getUbudehe() {
        return ubudehe;
    }

    public void setUbudehe(int ubudehe) {
        this.ubudehe = ubudehe;
    }

    public long getRescount() {
        return rescount;
    }

    public void setRescount(long rescount) {
        this.rescount = rescount;
    }

    public long getGuardcount() {
        return guardcount;
    }

    public void setGuardcount(long guardcount) {
        this.guardcount = guardcount;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Guard getChosenGuard() {
        return chosenGuard;
    }

    public void setChosenGuard(Guard chosenGuard) {
        this.chosenGuard = chosenGuard;
    }

}
