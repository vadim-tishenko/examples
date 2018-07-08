package com.example.demo.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by vadim.tishenko
 * on 16.06.2018 21:49.
 */
public interface TrainRepository extends CrudRepository<Train, Long> {
}
