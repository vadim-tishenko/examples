package ru.cwl.example.vaadinspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Operation {
    Long id;
//    @CsvBindByName(column = "Дата операции")
    LocalDateTime opDate;
//    @CsvBindByName(column = "Дата платежа")
    LocalDate payDate;
//    @CsvBindByName(column = "Номер карты")
    String cardNum;
//    @CsvBindByName(column = "Статус")
    String status;
//    @CsvBindByName(column = "Сумма операции")
    BigDecimal summ;
//    @CsvBindByName(column = "Валюта операции")
    CurrencyCode currency;
//    @CsvBindByName(column = "Сумма платежа")
    BigDecimal paySumm;
//    @CsvBindByName(column = "Валюта платежа")
    CurrencyCode payCurrency;
//    @CsvBindByName(column = "Кэшбэк")
    BigDecimal cashback;
//    @CsvBindByName(column = "Категория")
    String category;
//    @CsvBindByName(column = "MCC")
    String mcc;
//    @CsvBindByName(column = "Описание")
    String description;
//    @CsvBindByName(column = "Бонусы (включая кэшбэк)")
    BigDecimal bonus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOpDate() {
        return opDate;
    }

    public void setOpDate(LocalDateTime opDate) {
        this.opDate = opDate;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public BigDecimal getPaySumm() {
        return paySumm;
    }

    public void setPaySumm(BigDecimal paySumm) {
        this.paySumm = paySumm;
    }

    public CurrencyCode getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(CurrencyCode payCurrency) {
        this.payCurrency = payCurrency;
    }

    public BigDecimal getCashback() {
        return cashback;
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
