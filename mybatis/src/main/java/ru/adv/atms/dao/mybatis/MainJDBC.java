package ru.adv.atms.dao.mybatis;

import ru.adv.atms.core.domain.shipment.Shipment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainJDBC {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/testdb", "postgres",
                "postgres");


        long t0 = System.nanoTime();

        runSel(connection);
        long t1 = System.nanoTime();
        runSel(connection);

        long t2 = System.nanoTime();

        System.out.printf("vehicle:%s shipment:%s\n", (t1 - t0) / 1000000L, (t2 - t1) / 1000000L);


    }

    private static void runSel(Connection connection) throws SQLException {
        String selectTableSQL = "select id,date,status,carrier_id,customer_id,driver_id,vehicle_id,start,finish from shipment";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectTableSQL);
        List<Shipment> res = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            Long vehicleId = rs.getLong("vehicle_id");
            LocalDateTime start = rs.getObject("start", LocalDateTime.class);
            LocalDateTime finish = rs.getObject("start", LocalDateTime.class);
            Shipment shipment = new Shipment();
            shipment.getScheduledInterval().setStart(start);
            shipment.getScheduledInterval().setFinish(finish);
            res.add(shipment);
        }
        statement.close();
        rs.close();
    }
}
