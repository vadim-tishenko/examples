package ru.cwl.vaadin1.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by vadim.tishenko
 * on 29.04.2018 22:31.
 */
public class MyItems {
    private String field1;
    private float field2;
    private int field3;
    private LocalDate field4;
    private BigDecimal field5;

    public MyItems() {
    }

    public MyItems(String field1, float field2, int field3, String field4, String field5) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = LocalDate.parse(field4);
        this.field5 = new BigDecimal(field5);
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public float getField2() {
        return field2;
    }

    public void setField2(float field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    public LocalDate getField4() {
        return field4;
    }

    public void setField4(LocalDate field4) {
        this.field4 = field4;
    }

    public BigDecimal getField5() {
        return field5;
    }

    public void setField5(BigDecimal field5) {
        this.field5 = field5;
    }
}
