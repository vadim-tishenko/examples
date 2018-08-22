package ru.cwl.examples.mongo;

/**
 * Created by vadim.tishenko
 * on 05.08.2018 23:47.
 */

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public List<Contact> contactList = new ArrayList<>();
    public List<String> tags = new ArrayList<>();

    public Map<String, Object> map = new HashMap<>();
    public LocalDateTime dateTime = LocalDateTime.now();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s', cont=%s, tags=%s, map=%s, dt=%s]",
                id, firstName, lastName, contactList, tags, map,dateTime);
    }

}
