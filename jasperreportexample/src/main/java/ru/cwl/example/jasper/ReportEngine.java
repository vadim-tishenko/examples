package ru.cwl.example.jasper;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.extensions.ExtensionsEnvironment;
import net.sf.jasperreports.repo.DefaultRepositoryService;
import net.sf.jasperreports.repo.RepositoryService;
import net.sf.jasperreports.repo.Resource;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Created by tischenko on 24.01.2018 14:04.
 */

/**
 * в данной реализации решен вопрос с потерей контекста (пропадали картинки)
 * и решен вопрос с рускими буквами при экспорте jasperreports-fonts-1.0.jar
 */
public class ReportEngine {
    //private String basePath;
    private URI baseUri;

    SimpleJasperReportsContext myContext;

    private ReportEngine() {
    }

    public ReportEngine(URL baseUrl) throws URISyntaxException {
        this.baseUri = baseUrl.toURI();
        init();
    }


    public ReportEngine(Class clazz,String basePath) throws URISyntaxException {
        URL baseUrl = clazz.getResource(basePath);
        baseUri = baseUrl.toURI();
        init();
    }

    public ReportEngine(String basePath) {
        this(new File(basePath).toURI());
    }

    public ReportEngine(URI baseUri) {
        this.baseUri = baseUri;
        init();
    }

    private void init() {
        myContext = new SimpleJasperReportsContext();
        DefaultRepositoryService rs = new DefaultRepositoryService(myContext);
        rs.setFileResolver(new SimpleFileResolver(new File(baseUri)));
        myContext.setExtensions(RepositoryService.class, Collections.singletonList(rs));
    }

    void viewReport(JasperPrint jasperPrint) {
        JFrame frame = new JFrame("Report window title");
        frame.getContentPane().add(new JRViewer(jasperPrint));
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public JasperReport loadAndCompile(String path) throws JRException {
        File reportPattern = new File(baseUri.resolve(path));
        JasperDesign jasperDesign = JRXmlLoader.load(reportPattern);
        return JasperCompileManager.compileReport(jasperDesign);
    }

    public JasperPrint fillReport(JasperReport jasperReport, JRDataSource dataSource, Map<String, Object> parameters) throws JRException {
        JasperFillManager jasperFillManager = JasperFillManager.getInstance(myContext);
        return jasperFillManager.fill(jasperReport, parameters, dataSource);
    }

    public JasperPrint fillReport(JasperReport jasperReport, Collection<?> beanCollection, Map<String, Object> parameters) throws JRException {
        JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperFillManager jasperFillManager = JasperFillManager.getInstance(myContext);
        return jasperFillManager.fill(jasperReport, parameters, dataSource);
    }
}
