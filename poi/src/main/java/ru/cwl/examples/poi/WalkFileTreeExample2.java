package ru.cwl.examples.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by vadim.tishenko
 * on 14.07.2018 18:54.
 */
public class WalkFileTreeExample2  {

    public static void main(String[] args) {
        AzbukaDataExtractor ee=new AzbukaDataExtractor();
        Path p = Paths.get("c:/tmp/az");
        FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file: "+file);
                FileInputStream excelFile = new FileInputStream(file.toFile());// File(path+FILE_NAME));
                Workbook workbook = new XSSFWorkbook(excelFile);
                ee.extract(workbook);

                //System.out.println(file);
                return FileVisitResult.CONTINUE;
            }
        };

        try {
            Files.walkFileTree(p, fv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}