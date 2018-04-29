package ru.cwl.vaadin1.data;

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


    }
    public List<MyItems> getAllItems(){
        return myItems;
    }

    public List<Row> getAllOperations(){
        return res;
    }
}
