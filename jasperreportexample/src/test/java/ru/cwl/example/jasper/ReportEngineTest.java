package ru.cwl.example.jasper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.Test;

/**
 * Created by tischenko on 31.01.2018 17:21.
 */
public class ReportEngineTest {

    @Test
    public void reportGenerationTest() throws JRException, URISyntaxException {
        String REPORT_pattern = "Blank_A4.jrxml";

        ArrayList<DataItem> items = getDataItems();
        Map<String, Object> parameters = getDataCtx();
        parameters.put("parameter1", "value");

        // base folder
        URL baseUrl = ReportEngineTest.class.getResource("test_template/");
        ReportEngine reportEngine = new ReportEngine(baseUrl);
        JasperReport jasperReport = reportEngine.loadAndCompile(REPORT_pattern);
        JasperPrint jasperPrint = reportEngine.fillReport(jasperReport, items, parameters);

        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

    }

    /**
     * как можно создавать шаблоны отчетов?
     * взять тут https://community.jaspersoft.com/project/jaspersoft-studio дизайнер шаблонов отчетов.
     *
     * создать источники данных для отчета коллекчцию бинов для повторяющихся данных и мапу
     * с параметрами для одиночных данных. (1)
     * создать ReportEngine с указанием от какой папки будут отсчитываться относительные пути. (2)
     * загрузить и скомпилировать шаблон отчета *.jrxml (3)
     * заполнить скомпилированный отчет данными (4)
     * далее можно просмотреть (5)
     * или экспортировать (6)
     *
     */
    public static void main(String[] args) throws JRException, URISyntaxException {
        String REPORT_pattern = "Blank_A4.jrxml";

        ArrayList<DataItem> items = getDataItems(); //(1)
        Map<String, Object> parameters = getDataCtx(); //(1)
        parameters.put("parameter1", "value"); //(1)

        ReportEngine reportEngine = new ReportEngine(ReportEngineTest.class,"test_template/"); //(2)
        JasperReport jasperReport = reportEngine.loadAndCompile(REPORT_pattern); //(3)
        JasperPrint jasperPrint = reportEngine.fillReport(jasperReport, items, parameters); //(4)

        // посмотреть во вьювере
        reportEngine.viewReport(jasperPrint); //(5)

        // сохранить в файл
        String deskTop = "C:\\tmp\\";
        JasperExportManager.exportReportToPdfFile(jasperPrint, deskTop + "report.pdf"); //(6)

        // можем сохранить в массив
        //byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint); //(6)

    }


     static ArrayList<DataItem> getDataItems() {
        ArrayList<DataItem> items = new ArrayList<>();
        items.add(new DataItem("Guest1", "номер простой", "100 руб"));
        items.add(new DataItem("Guest2", "номер люкс", "200 руб"));
        return items;
    }

     static Map<String, Object> getDataCtx() {

        Map<String, Object> ctx = new HashMap<>();


        Map<String, String> org = new HashMap<>();
        org.put("name", "ООО 'Бест'");
        org.put("code", "FEST_CODE");

        org.put("contact", "agentName");
        org.put("tel", "agentTel");
        org.put("mail", "agentMail");
        ctx.put("org", org);
        // отель и номер
        Map<String, Object> agr = new HashMap<>();
        agr.put("number", "da.getDealNumber()");
        agr.put("date", "da.getDealDate()");
        agr.put("bookingForm", "da.getBookingForm()");
        agr.put("participant", "da.getParticipant()");

        ctx.put("agr", agr);

        Map<String, String> room = new HashMap<>();
        room.put("name", "@room name #123456 Р&K");
        room.put("checkIn", "hp.getCheckInDate().toString()");
        room.put("checkOut", "hp.getCheckOutDate().toString()");
        room.put("count", "1");
        room.put("hotel_name", "da.getHotelName()");
        room.put("hotel_city", "da.getHotelLocation().getCaption()");
   /*     for (Traveller traveller : "hp.getGuests()") {

        }*/

        room.put("guests", "@гость1, гость2");
        room.put("price", "hp.getPrice().toString()");

        //

        ctx.put("room", room);
        ctx.put("curDate", LocalDate.now().toString());
        return ctx;
    }

}