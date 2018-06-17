package com.example.demo.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by vadim.tishenko
 * on 16.06.2018 21:50.
 */
public interface VehicleRepository extends CrudRepository<Vehicle,Long> {
    Vehicle findByName(String name);
}
