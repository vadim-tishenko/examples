package ru.cwl.example.vaadinspringboot.service;

import com.opencsv.bean.CsvBindByName;
import org.apache.tomcat.jni.Local;

import java.math.BigDecimal;
import java.util.Locale;

import static java.util.Locale.FRANCE;

public class OperationDto {
    static final Locale lo = Locale.FRANCE;
    @CsvBindByName(column = "Дата операции")
    String opDate;
    @CsvBindByName(column = "Дата платежа")
    String payDate;
    @CsvBindByName(column = "Номер карты")
    String cardNum;
    @CsvBindByName(column = "Статус")
    String status;
    @CsvBindByName(column = "Сумма операции")
    String summ;
    @CsvBindByName(column = "Валюта операции")
    String currency;
    @CsvBindByName(column = "Сумма платежа")
    String paySumm;
    @CsvBindByName(column = "Валюта платежа")
    String payCurrency;
    @CsvBindByName(column = "Кэшбэк")
    String cashback;
    @CsvBindByName(column = "Категория")
    String category;
    @CsvBindByName(column = "MCC")
    String mcc;
    @CsvBindByName(column = "Описание")
    String description;
    @CsvBindByName(column = "Бонусы (включая кэшбэк)")
    String bonus;

}
