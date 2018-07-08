package ru.cwl.vaadin1.data;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 29.04.2018 22:30.
 */
public class DataService {
    private List<MyItems> myItems;
    private List<Row> res;
    private List<OatmoRow> oatmoList;
    public DataService() {
        myItems=new ArrayList<>();
        myItems.add(new MyItems("f1",1,1,"2018-01-01","100.0"));
        myItems.add(new MyItems("f2",2.3f,20,"2018-12-01","200.0"));
        myItems.add(new MyItems("f3",3.5f,100,"2028-05-01","300.44"));

        Reader reader=null;
        try {
            reader = new InputStreamReader(DataService.class.getResourceAsStream("/srcdata/operations_all.csv"), "windows-1251");
            CsvReader csvReader = new CsvReader();
            res = csvReader.read(reader);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(Reader r=new InputStreamReader(DataService.class.getResourceAsStream("/srcdata/okmto/data-20180426t000000-structure-20150128t000000.csv"), "windows-1251")){
            oatmoList = new CsvToBeanBuilder(r).withType(OatmoRow.class).withSeparator(';').build().parse();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* try(Reader in = new InputStreamReader(DataService.class.getResourceAsStream("/srcdata/okmto/data-20180426t000000-structure-20150128t000000.csv"), "windows-1251")) {
            final CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter(';'));
            //Iterable<OatmoRow> records = CSVFormat.DEFAULT.withDelimiter(';').parse(in);
            int n=0;
            for (CSVRecord record : parser) {
                System.out.println(n++);
                String columnOne = record.get(0);
                String columnTwo = record.get(1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    public List<MyItems> getAllItems(){
        return myItems;
    }

    public List<Row> getAllOperations(){
        return res;
    }

    public List<OatmoRow> getOatmoList() {
        return oatmoList;
    }
}
