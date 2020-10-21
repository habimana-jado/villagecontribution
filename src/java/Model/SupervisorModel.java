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
import Domain.Resident;
import Domain.Supervisor;
import Domain.Transaction;
import Domain.User;
import java.text.DecimalFormat;
import java.text.ParseException;
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

/**
 *
 * @author student
 */
@ManagedBean
@SessionScoped
public class SupervisorModel {

    private Supervisor supervisor = new Supervisor();

    private Supervisor supervisorDetails = new Supervisor();

    SupervisorDao supervisorDao = new SupervisorDao();

    private String searchKey = new String();

    private List<Supervisor> supervisors;

    private List<Supervisor> supervisorelle;

    private List<Resident> residents;

    private List<Guard> guards;

    private String cid = new String();

    private User u = new User();

    private String type;

    private long empId;

    private long servId;

    private String password;

    private Supervisor searchsup = new Supervisor();

    private Admin sup = new Admin();

    private List<String> choosenImage = new ArrayList<>();

    private long rescount;

    private long supcount;

    private long guardcount;

    private double conttotal;

    private List<Transaction> transactions = new ArrayList<>();

    private String num = new String();

    private double amt = 0.0;
    
    @PostConstruct
    public void init() {
        supervisorInit();
        nameInit();
        residentInit();
        guardInit();
        transactionInit();
    }

    public void calculate() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getAdmin();
        transactions = new TransactionDao().findByAdmin(a);
        
        amt = 0.0;
        for (Transaction t : transactions) {
            if (num.matches("January") && t.getJanuary() > 0) {
                amt = amt + t.getJanuary();
            }else if (num.matches("February") && t.getFebruary()> 0) {
                amt = amt + t.getFebruary();
            }else if (num.matches("March") && t.getMarch()> 0) {
                amt = amt + t.getMarch();
            }else if (num.matches("April") && t.getApril()> 0) {
                amt = amt + t.getApril();
            }else if (num.matches("May") && t.getMay()> 0) {
                amt = amt + t.getMay();
            }else if (num.matches("June") && t.getJune()> 0) {
                amt = amt + t.getJune();
            }else if (num.matches("July") && t.getJuly() > 0) {
                amt = amt + t.getJuly();
            }else if (num.matches("August") && t.getAugust()> 0) {
                amt = amt + t.getAugust();
            }else if (num.matches("September") && t.getSeptember()> 0) {
                amt = amt + t.getSeptember();
            }else if (num.matches("October") && t.getOctober()> 0) {
                amt = amt + t.getOctober();
            }else if (num.matches("November") && t.getNovember()> 0) {
                amt = amt + t.getNovember();
            }else if (num.matches("December") && t.getDecember()> 0) {
                amt = amt + t.getDecember();
            }
        }
    }

    public void transactionInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getAdmin();
        transactions = new TransactionDao().findByAdmin(a);
    }

    public void nameInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        sup = x.getAdmin();
    }

    public void supervisorInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getAdmin();
        supervisors = new SupervisorDao().findByAdmin(a);
        supcount = new SupervisorDao().supervisorcount(a, "Available");
    }

    public void residentInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getAdmin();
        residents = new ResidentDao().findByAdmin(a, "Available");
        rescount = new ResidentDao().residentcount(a, "Available");
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

                pdfc6 = new PdfPCell(new Phrase(x.getJanuary()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJanuary();
                
            }else if (num.matches("February") && x.getFebruary()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);
                
                pdfc6 = new PdfPCell(new Phrase(x.getFebruary()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getFebruary();
                
            }else if (num.matches("March") && x.getMarch()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMarch()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getMarch();
                
            }else if (num.matches("April") && x.getApril()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getApril()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getApril();
                
            }else if (num.matches("May") && x.getMay()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMay()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getMay();
                
            }else if (num.matches("June") && x.getJune()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJune()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getJune();
                
            }else if (num.matches("July") && x.getJuly()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJuly()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getJuly();
                
            }else if (num.matches("August") && x.getAugust()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getAugust()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getAugust();
                
            }else if (num.matches("September") && x.getSeptember()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getSeptember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getSeptember();
                
            }else if (num.matches("October") && x.getOctober()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);
                
                pdfc6 = new PdfPCell(new Phrase(x.getOctober()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);

                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getOctober();
                
            }else if (num.matches("November") && x.getNovember()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getNovember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getNovember();
                
            }else if (num.matches("December") && x.getDecember()> 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getDecember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
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

                pdfc6 = new PdfPCell(new Phrase(x.getJanuary()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getJanuary();
                
            }else if (num.matches("February") && x.getFebruary() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getFebruary()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);

                amt = amt + x.getFebruary();
                
            }else if (num.matches("March") && x.getMarch() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMarch()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getMarch();
                
            }else if (num.matches("April") && x.getApril() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getApril()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
            }else if (num.matches("May") && x.getMay() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getMay()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getApril();
                
            }else if (num.matches("June") && x.getJune() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJune()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getJune();
                
            }else if (num.matches("July") && x.getJuly() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getJuly()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getJuly();
                
            }else if (num.matches("August") && x.getAugust() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getAugust()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getAugust();
                
            }else if (num.matches("September") && x.getSeptember() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getSeptember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getSeptember();
                
            }else if (num.matches("October") && x.getOctober() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getOctober()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getOctober();
                
            }else if (num.matches("November") && x.getNovember() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getNovember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
                pdfc4.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc4);               
                
                amt = amt + x.getNovember();
                
            }else if (num.matches("December") && x.getDecember() < 0) {
               
                pdfc5 = new PdfPCell(new Phrase(x.getResident().getName(), font6));
                pdfc5.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc5);

                pdfc6 = new PdfPCell(new Phrase(x.getDecember()+"", font6));
                pdfc6.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc6);
                
                pdfc3 = new PdfPCell(new Phrase(x.getResident().getUbudehe() + "", font6));
                pdfc3.setBorder(Rectangle.NO_BORDER);
                tables.addCell(pdfc3);

                pdfc4 = new PdfPCell(new Phrase(x.getResident().getPhone()+ "", font6));
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

    public void guardInit() {
        User x = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
        Admin a = x.getAdmin();
        guards = new GuardDao().findByAdmin(a);
        guardcount = new GuardDao().guardcount(a, "Available");
    }
    
    public void remove(Supervisor r) {
        r.setStatus("Moved");
        new SupervisorDao().Update(r);

        for (User u : new UserDao().findBySupervisor(r)) {
            u.setAccess("Blocked");
            new UserDao().Update(u);
        }
        supervisorInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Supervisor Removed"));
    }
    
    public void removeGuard(Guard r) {
        r.setStatus("Moved");
        new GuardDao().Update(r);

        guardInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Guard Removed"));
    }


    public void Upload(FileUploadEvent event) {
        choosenImage.add(new FileUpload().Upload(event, "C:\\Users\\Issa\\Desktop\\VillageContributionApp\\web\\uploads\\"));
    }

    public void createSupervisor() throws ParseException, Exception {
        if (choosenImage.isEmpty()) {
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Upload Supervisor's Profile Image"));
        } else {
            User z = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session"); //hano nuku getting the username
            Admin a = z.getAdmin();

            for (String x : choosenImage) {
                supervisor.setImage(x);
            }

            Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(cid);
            supervisor.setStatus("Available");
            supervisor.setHireDate(dt);
            supervisor.setAdmin(a);
            supervisorDao = new SupervisorDao();
            supervisorDao.register(supervisor);
            String pass = new PassCode().encrypt(password);
            u.setPassword(pass);
            u.setAccess("Supervisor");
            u.setStatus("Active");
            u.setSupervisor(supervisor);
            new UserDao().register(u);
            u = new User();
            supervisor = new Supervisor();

            supervisorInit();
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Supervisor Registered"));
        }
    }

    public String navigateupdate(Supervisor x) {
        searchsup = x;
        return "updatesupervisor.xhtml?faces-redirect=true";
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
        Paragraph title = new Paragraph();
        //BEGIN page
        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 9, Font.ITALIC, new Color(90, 255, 20));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Resident List Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase("Resident Name", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);
        PdfPCell cell = new PdfPCell(new Phrase("Phone", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);
        PdfPCell c1 = new PdfPCell(new Phrase("Email", font2));
        c1.setBorder(Rectangle.BOTTOM);
        tables.addCell(c1);

        PdfPCell c4 = new PdfPCell(new Phrase("Status", font2));
        c4.setBorder(Rectangle.BOTTOM);
        tables.addCell(c4);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Resident x : residents) {
            pdfc5 = new PdfPCell(new Phrase(x.getName(), font6));
            pdfc5.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc5);
            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc3);
            pdfc4 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

            pdfc4 = new PdfPCell(new Phrase(x.getStatus() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

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
    
    public void generateSupervisorPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
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
        Paragraph title = new Paragraph();
        //BEGIN page
        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 9, Font.ITALIC, new Color(90, 255, 20));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Supervisor List Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase("Supervisor Name", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);
        PdfPCell cell = new PdfPCell(new Phrase("Phone", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);
        PdfPCell c1 = new PdfPCell(new Phrase("Email", font2));
        c1.setBorder(Rectangle.BOTTOM);
        tables.addCell(c1);

        PdfPCell c4 = new PdfPCell(new Phrase("Status", font2));
        c4.setBorder(Rectangle.BOTTOM);
        tables.addCell(c4);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        supervisorInit();
        for (Supervisor x : supervisors) {
            pdfc5 = new PdfPCell(new Phrase(x.getName(), font6));
            pdfc5.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc5);
            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc3);
            pdfc4 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

            pdfc4 = new PdfPCell(new Phrase(x.getStatus() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

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
    
    public void generateGuardPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {
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
        Paragraph title = new Paragraph();
        //BEGIN page
        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 9, Font.ITALIC, new Color(90, 255, 20));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("VILLAGE SECURITY CONTRIBUTION SYSTEM\n"));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("Guard List Report\n", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase("Guard Name", font2));
        cell1.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell1);
        PdfPCell cell = new PdfPCell(new Phrase("Phone", font2));
        cell.setBorder(Rectangle.BOTTOM);
        tables.addCell(cell);
        PdfPCell c1 = new PdfPCell(new Phrase("Email", font2));
        c1.setBorder(Rectangle.BOTTOM);
        tables.addCell(c1);

        PdfPCell c4 = new PdfPCell(new Phrase("Status", font2));
        c4.setBorder(Rectangle.BOTTOM);
        tables.addCell(c4);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        guardInit();
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

            pdfc4 = new PdfPCell(new Phrase(x.getStatus() + "", font6));
            pdfc4.setBorder(Rectangle.NO_BORDER);
            tables.addCell(pdfc4);

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

    public void updateSupervisor() {
        supervisorDao.Update(searchsup);
        supervisorDetails = new Supervisor();

        supervisorInit();
        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage("Supervisor updated"));
    }

    public void deleteSupervisor() {
        supervisorDao.Delete(supervisor);
        supervisors = supervisorDao.FindAll(Supervisor.class);

        FacesContext ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor Deleted", ""));

    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Supervisor getSupervisorDetails() {
        return supervisorDetails;
    }

    public void setSupervisorDetails(Supervisor supervisorDetails) {
        this.supervisorDetails = supervisorDetails;
    }

    public SupervisorDao getSupervisorDao() {
        return supervisorDao;
    }

    public void setSupervisorDao(SupervisorDao supervisorDao) {
        this.supervisorDao = supervisorDao;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public List<Supervisor> getSupervisorelle() {
        return supervisorelle;
    }

    public void setSupervisorelle(List<Supervisor> supervisorelle) {
        this.supervisorelle = supervisorelle;
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

    public Supervisor getSearchsup() {
        return searchsup;
    }

    public void setSearchsup(Supervisor searchsup) {
        this.searchsup = searchsup;
    }

    public Admin getSup() {
        return sup;
    }

    public void setSup(Admin sup) {
        this.sup = sup;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public List<Guard> getGuards() {
        return guards;
    }

    public void setGuards(List<Guard> guards) {
        this.guards = guards;
    }

    public List<String> getChoosenImage() {
        return choosenImage;
    }

    public void setChoosenImage(List<String> choosenImage) {
        this.choosenImage = choosenImage;
    }

    public long getRescount() {
        return rescount;
    }

    public void setRescount(long rescount) {
        this.rescount = rescount;
    }

    public long getSupcount() {
        return supcount;
    }

    public void setSupcount(long supcount) {
        this.supcount = supcount;
    }

    public long getGuardcount() {
        return guardcount;
    }

    public void setGuardcount(long guardcount) {
        this.guardcount = guardcount;
    }

    public double getConttotal() {
        return conttotal;
    }

    public void setConttotal(double conttotal) {
        this.conttotal = conttotal;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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

}
