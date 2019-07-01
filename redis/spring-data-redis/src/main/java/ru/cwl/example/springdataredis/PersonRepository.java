package ru.cwl.example.springdataredis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface PersonRepository
        extends KeyValueRepository<Person, String> {
    List<Person> findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);
}