package ru.adv.atms.dao.mybatis;

import com.concretepage.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import ru.adv.atms.core.domain.Interval;
import ru.adv.atms.core.domain.fleet.Vehicle;
import ru.adv.atms.core.domain.shipment.Shipment;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        //mapper.insertVillage(village);
        for (int i=0;i<10;i++) {
            SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
            VehicleMapper mapper = session.getMapper(VehicleMapper.class);
            long t0 = System.nanoTime();
            List<Vehicle> res = mapper.getAllVehicle();

            long t1 = System.nanoTime();

            LocalDateTime now = LocalDateTime.now();
            now=LocalDateTime.of(2018,4,10,0,0);
            List<Shipment> res2 = mapper.getAllShipmentsInterval(now.minusDays(2),now);
            //List<Shipment> res2 = mapper.getAllShipments();
            long t2 = System.nanoTime();

            System.out.printf("vehicle:%s shipment:%s\n", (t1 - t0) / 1000000L, (t2 - t1) / 1000000L);
            session.commit();
            session.close();
        }

    }
}
