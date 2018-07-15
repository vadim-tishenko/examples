package ru.cwl.examples.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by vadim.tishenko
 * on 14.07.2018 10:57.
 */
public class ReadFile {
    static final String path="C:\\tmp\\30.06\\";
    static final String FILE_NAME="c:\\tmp\\az\\01.06\\Копия Заказ транспорта новый  МТЭК Хлеб.xlsx";
/*    public static void main(String[] args) {


    }*/

    public static void main(String[] args) {

        AzbukaDataExtractor ee=new AzbukaDataExtractor();
        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            ee.extract(workbook);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
