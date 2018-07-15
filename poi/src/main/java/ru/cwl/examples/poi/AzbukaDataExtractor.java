package ru.cwl.examples.poi;

import org.apache.poi.ss.usermodel.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vadim.tishenko
 * on 14.07.2018 11:31.
 */

public class AzbukaDataExtractor {

    private final Pattern palPattern = Pattern.compile("(\\d{1,2}) пал", Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
    private final Pattern tripPattern = Pattern.compile("(\\d) рейс", Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
    private final Pattern datePattern = Pattern.compile("(\\d{1,2}\\.\\d{2}\\.\\d{4})");
    private final Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2})");
    private final ZoneId defaultZoneId = ZoneId.systemDefault();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public AzbukaDataExtractor() {

    }

    public void extract(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

        int startRow = -1;
        for (int i = 0; i < 50; i++) {
            String v = value(sheet, i, 0);
            if ("№".equals(v)) {
                startRow = i + 1;
                break;
            }
        }

        if (startRow == -1) {
            System.out.println("bad format!");
            return;
        }


        String tempMode = "не определен";
        for (int i = 0; i < 50; i++) {
            String v = value(sheet, i, 7);
            if ("Температурный режим:".equals(v)) {
                tempMode=value(sheet, i, 8);
                break;
            }
        }

        String fromAddress = value(sheet, startRow-27, 7);
        List<DataRow> resultRows = new ArrayList<>();
        int num = 0;
        int pallets = 0;

        for (int rowNum = startRow; rowNum < 100; rowNum++) {

            DataRow dr = new DataRow();
            dr.tripNo=1;
            Row row = sheet.getRow(rowNum);

            Cell c0 = row.getCell(0);
            if (c0!=null && c0.getCellTypeEnum() == CellType.NUMERIC) {
                num = (int) c0.getNumericCellValue();
            }
            dr.num = num;

            String s = "";
            for (int ii = 0; ii < 4; ii++) {
                s += row.getCell(ii, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString() + '|';
            }
            dr.allText = s;

            // 01-июл-2018
            // 30.06-01.07.2018
            Cell c1 = row.getCell(1);
            if (c1.getCellTypeEnum() == CellType.NUMERIC) {
                Date d = c1.getDateCellValue();
                Instant instant = d.toInstant();
                LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
                dr.start = localDate.atStartOfDay();
            } else {
                dr.over += " 2:" + c1.toString();
            }

            // 02:00 1 Рейс Дейли
            // 01.07.2018 11:00 2 Рейс
            Cell c2 = row.getCell(2);

            if (c1.toString().isEmpty() && c2.toString().isEmpty()) break;

            if(c2.getCellTypeEnum()==CellType.NUMERIC){
                Date d = c2.getDateCellValue();
                Instant instant = d.toInstant();
                LocalTime localTime = instant.atZone(defaultZoneId).toLocalTime();
                dr.start = dr.start.toLocalDate().atTime(localTime);
            }else{
                String hourAndTrip = c2.toString();

                Matcher tripMatcher = tripPattern.matcher(hourAndTrip);
                if (tripMatcher.find()) {
                    dr.tripNo = Integer.parseInt(tripMatcher.group(1));
                    hourAndTrip = tripMatcher.replaceAll("");
                }

                Matcher dateMatcher = datePattern.matcher(hourAndTrip);
                if (dateMatcher.find()) {
                    String dateStr = dateMatcher.group(1);
                    LocalDate ld = LocalDate.parse(dateStr, dateFormatter);
                    dr.start = ld.atStartOfDay();
                    hourAndTrip = dateMatcher.replaceAll("");
                }

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");

                Matcher timeMatcher = timePattern.matcher(hourAndTrip);
                if (timeMatcher.find()) {
                    String timeStr = timeMatcher.group(1);
                    LocalTime lt = LocalTime.parse(timeStr, timeFormatter);
                    dr.start = dr.start.toLocalDate().atTime(lt);
                    hourAndTrip = timeMatcher.replaceAll("");
                }

                hourAndTrip = hourAndTrip.trim();
                if (!hourAndTrip.isEmpty()) {
                    dr.over += " 3:" + hourAndTrip;
                }
            }

            // 15 пал СК
            Cell c3 = row.getCell(3);

            Matcher m = palPattern.matcher(c3.toString());
            final boolean b = m.find();
            if (b) {
                pallets = Integer.parseInt(m.group(1));
                final String ov = m.replaceFirst("").trim();
                if (!ov.isEmpty()) {
                    dr.over += " 4:" + ov;
                }
            }
            dr.pallets = pallets;

            resultRows.add(dr);

        }

        System.out.println(fromAddress);
        System.out.println("темп: "+tempMode);
        for (DataRow resultRow : resultRows) {
            System.out.println(resultRow);
        }
    }

    private String value(Sheet sheet, int rowNum, int colNum) {
        return getCell(sheet, rowNum, colNum).toString();
    }

    private Cell getCell(Sheet sheet, int rowNum, int colNum) {
        return sheet.getRow(rowNum).getCell(colNum);
    }

    class DataRow {
        int num;
        LocalDateTime start;
        int pallets;
        int tripNo;
        String allText;
        String over = "";

        @Override
        public String toString() {
            return String.format("dr{%2s.%1s %16s п%-2s a:%-60s np:%s}", num, tripNo, start, pallets,  allText, over);
        }

    }
}
