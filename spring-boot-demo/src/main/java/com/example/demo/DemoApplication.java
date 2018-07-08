package com.example.demo;

import com.example.demo.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        log.info("start");
        SpringApplication.run(DemoApplication.class, args);
        log.info("finish");
    }

    @Bean
    public CommandLineRunner demo2(VehicleRepository vehicleRepository,
                                   TrainRepository trainRepository,
                                   ShipmentRepository shipmentRepository) {
        return (args) -> {
            vehicleRepository.save(new Vehicle("машина 1"));
            vehicleRepository.save(new Vehicle("машина 2"));

            Vehicle v1 = vehicleRepository.findByName("машина 1");
            Vehicle v2 = vehicleRepository.findByName("машина 2");

            Train t1 = new Train();
            t1.getVehicles().add(v1);
            t1.getVehicles().add(v2);
            trainRepository.save(t1);

            Train t2 = new Train();
            t2.getVehicles().add(v1);
            t2.getVehicles().add(v2);
            trainRepository.save(t2);

            Shipment s1=new Shipment();
            s1.getVehicles().add(v1);
            s1.getVehicles().add(v2);
            shipmentRepository.save(s1);

            Shipment s2=new Shipment();
            s2.getVehicles().add(v1);
            s2.getVehicles().add(v2);
            shipmentRepository.save(s2);

            System.out.printf("%s %s\n", v1, v2);
        };
    }
}
