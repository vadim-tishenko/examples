package com.example.demo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vadim.tishenko
 * on 05.06.2018 21:03.
 */
public class UserDto {
    protected int id;
    protected String name;

    public UserDto() {
    }

    public UserDto(int i, String name) {
        this.id=i;
        this.name=name;
    }

    @JsonProperty("idddd")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
