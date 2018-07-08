package ru.cwl.example.vaadinspringboot.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import ru.cwl.example.vaadinspringboot.model.CurrencyCode;
import ru.cwl.example.vaadinspringboot.model.Operation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CsvImportService {
    //  http://opencsv.sourceforge.net/
    public List<OperationDto> read() {
        InputStream in = getClass().getResourceAsStream("/data/operations.csv");

        try (InputStreamReader isr = new InputStreamReader(in, "windows-1251")) {
            List<OperationDto> beans = new CsvToBeanBuilder<OperationDto>(isr)
                    .withType(OperationDto.class)
                    .withSeparator(';')
                    .build().parse();
            return beans;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();


    }

    public List<Operation> readOperationList() {
        List<OperationDto> a = read();
        List<Operation> result = new ArrayList<>();

        long i = 0;
        for (OperationDto dto : a) {
            Operation map = map(dto);
            map.setId(++i);
            result.add(map);
        }
        return result;
    }


    Operation map(OperationDto dto) {
//06.07.2018 00:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");


        Operation v = new Operation();
        v.setOpDate(LocalDateTime.parse(dto.opDate, formatter));
        if (!dto.payDate.isEmpty()) {
            v.setPayDate(LocalDate.parse(dto.payDate, formatter2));
        }
        v.setCardNum(dto.cardNum);
        v.setStatus(dto.status);
        v.setDescription(dto.description);
        v.setCashback(toBD(dto.cashback));
        v.setSumm(toBD(dto.summ));
        v.setCurrency(CurrencyCode.RUB);
        v.setPaySumm(toBD(dto.paySumm));
        v.setPayCurrency(CurrencyCode.RUB);
        v.setCategory(dto.category);
        v.setMcc(dto.mcc);
        v.setBonus(toBD(dto.bonus));
        return v;
    }
    BigDecimal toBD(String s){
        if(s==null || s.isEmpty()){
            return null;

        }
        return new BigDecimal(s.replace(",","."));
    }
}
