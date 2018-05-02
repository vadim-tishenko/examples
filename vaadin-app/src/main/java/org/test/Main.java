package org.test;

import ru.cwl.vaadin1.data.DataService;
import ru.cwl.vaadin1.data.OatmoRow;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 02.05.2018 23:50.
 */
public class Main {
    public static void main(String[] args) {
        DataService ds=new DataService();
        List<OatmoRow> oatmoList = ds.getOatmoList();
        System.out.printf("%s",oatmoList.size());
        System.out.println(oatmoList);
    }
}
