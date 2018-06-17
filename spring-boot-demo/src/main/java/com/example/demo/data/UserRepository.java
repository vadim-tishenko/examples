package com.example.demo.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 05.06.2018 21:32.
 */
public interface UserRepository extends CrudRepository<User,Integer> {
    List<User> findAll();
    User findByName(String name);
}
