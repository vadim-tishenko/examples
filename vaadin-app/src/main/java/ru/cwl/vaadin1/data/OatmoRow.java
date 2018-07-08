package ru.cwl.vaadin1.data;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Created by vadim.tishenko
 * on 02.05.2018 23:27.
 * Классификатор ОКТМО      ,,,
 * --------------------------------------------------,,,
 * Разделитель между полями ...... < ; >,,,
 * Признак конца позиции(записи) - <    >,,,
 * Объем выгрузки: Позиций - 201126,,,
 * ,,,
 * field name,english description,russian description,format
 * TER,region code,Код региона,varchar(2)
 * KOD1,area code/city MO,Код района/города МО,varchar(3)
 * KOD2,the code settlement MO,Код поселения МО,varchar(3)
 * KOD3,code locality MO,Код населенного пункта МО,varchar(3)
 * KC,контрольное число,numerek(1),
 * RAZDEL,code section,Код раздела,varchar(1)
 * NAME1,the name of the site,Наименование территории,varchar(500)
 * Centrum,additional information,Дополнительная информация,varchar(250)
 * NomDescr,Описание,varchar(8000),
 * NomAkt,Номер изменения,numerek(3),
 * Status,Тип изменения,numerek(1),
 * DateUtv,Дата принятия,data(10),
 * DateVved,Дата введения,data(10),
 */
public class OatmoRow {
    @CsvBindByPosition(position = 0)
    String ter;
    @CsvBindByPosition(position = 1)
    String kod1;
    @CsvBindByPosition(position = 2)
    String kod2;
    @CsvBindByPosition(position = 3)
    String kod3;
    @CsvBindByPosition(position = 4)
    String kc;
    @CsvBindByPosition(position = 5)
    String razdel;
    @CsvBindByPosition(position = 6)
    String name1;
    @CsvBindByPosition(position = 7)
    String centrum;
    @CsvBindByPosition(position = 8)
    String nomDescr;
    @CsvBindByPosition(position = 9)
    String nomAkt;
    @CsvBindByPosition(position = 10)
    String status;
    @CsvBindByPosition(position = 11)
    String dateUtv;
    @CsvBindByPosition(position = 12)
    String dateVved;


    public OatmoRow() {
    }

    public String getTer() {
        return ter;
    }

    public void setTer(String ter) {
        this.ter = ter;
    }

    public String getKod1() {
        return kod1;
    }

    public void setKod1(String kod1) {
        this.kod1 = kod1;
    }

    public String getKod2() {
        return kod2;
    }

    public void setKod2(String kod2) {
        this.kod2 = kod2;
    }

    public String getKod3() {
        return kod3;
    }

    public void setKod3(String kod3) {
        this.kod3 = kod3;
    }

    public String getKc() {
        return kc;
    }

    public void setKc(String kc) {
        this.kc = kc;
    }

    public String getRazdel() {
        return razdel;
    }

    public void setRazdel(String razdel) {
        this.razdel = razdel;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getCentrum() {
        return centrum;
    }

    public void setCentrum(String centrum) {
        this.centrum = centrum;
    }

    public String getNomDescr() {
        return nomDescr;
    }

    public void setNomDescr(String nomDescr) {
        this.nomDescr = nomDescr;
    }

    public String getNomAkt() {
        return nomAkt;
    }

    public void setNomAkt(String nomAkt) {
        this.nomAkt = nomAkt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateUtv() {
        return dateUtv;
    }

    public void setDateUtv(String dateUtv) {
        this.dateUtv = dateUtv;
    }

    public String getDateVved() {
        return dateVved;
    }

    public void setDateVved(String dateVved) {
        this.dateVved = dateVved;
    }

    @Override
    public String toString() {
        return "OatmoRow{" +
                "ter='" + ter + '\'' +
                ", kod1='" + kod1 + '\'' +
                ", kod2='" + kod2 + '\'' +
                ", kod3='" + kod3 + '\'' +
                ", kc='" + kc + '\'' +
                ", razdel='" + razdel + '\'' +
                ", name1='" + name1 + '\'' +
                ", centrum='" + centrum + '\'' +
                ", nomDescr='" + nomDescr + '\'' +
                ", nomAkt='" + nomAkt + '\'' +
                ", status='" + status + '\'' +
                ", dateUtv='" + dateUtv + '\'' +
                ", dateVved='" + dateVved + '\'' +
                '}';
    }
}
