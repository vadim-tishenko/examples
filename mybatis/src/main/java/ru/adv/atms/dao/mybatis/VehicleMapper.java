package ru.adv.atms.dao.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import ru.adv.atms.core.domain.fleet.Vehicle;
import ru.adv.atms.core.domain.shipment.Shipment;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id,name FROM vehicle")
    List<Vehicle> getAllVehicle();

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "scheduledInterval.start", column = "start"),
            @Result(property = "scheduledInterval.finish", column = "finish")
    })
    @Select("select id,date,status,carrier_id,customer_id,driver_id,vehicle_id,start,finish from shipment")
    List<Shipment> getAllShipments();

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "scheduledInterval.start", column = "start"),
            @Result(property = "scheduledInterval.finish", column = "finish")
    })
    @Select("select id,date,status,carrier_id,customer_id,driver_id,vehicle_id,start," +
            "finish from shipment" +
            " where start>#{start_p}  and finish<#{finish_p}")
    List<Shipment> getAllShipmentsInterval(@Param("start_p") LocalDateTime start, @Param("finish_p") LocalDateTime finish);
}
