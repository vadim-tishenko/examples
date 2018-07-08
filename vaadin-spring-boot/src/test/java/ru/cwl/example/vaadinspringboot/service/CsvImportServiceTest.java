package ru.cwl.example.vaadinspringboot.service;

import org.junit.Test;
import ru.cwl.example.vaadinspringboot.model.Operation;

import java.util.List;

import static org.junit.Assert.*;

public class CsvImportServiceTest {

    @Test
    public void readOperationList() {
        CsvImportService srv=new CsvImportService();
        List<Operation> res = srv.readOperationList();
    }
}