package com.example.demo.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vadim.tishenko
 * on 16.06.2018 20:57.
 */
@Entity
public class Train {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToMany
    /*@JoinTable(name = "TRAIN_VEHICLE",
            joinColumns = @JoinColumn(name = "TRAIN_FK"),
            inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID")
    )*/
    private Set<Vehicle> vehicles =new HashSet<>();

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }
}
