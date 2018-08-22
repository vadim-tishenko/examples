package ru.cwl.examples.mongo;

/**
 * Created by vadim.tishenko
 * on 05.08.2018 23:48.
 */
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByTags(String tag);


}
