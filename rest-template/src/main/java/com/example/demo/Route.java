package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vadim.tishenko
 * on 23.08.2018 23:09.
 */
public class Route {
    @JsonProperty("weight_name")
    String weightName;//": "routability",
    double weight;//": 901.2,
    double duration;//": 660.7,
    double distance; //: 4127.7

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
