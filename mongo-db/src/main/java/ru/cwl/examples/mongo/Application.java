package ru.cwl.examples.mongo;

/**
 * Created by vadim.tishenko
 * on 05.08.2018 23:48.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //repository.deleteAll();

        // save a couple of customers
        final Customer entity = new Customer("Alice", "Smith");
        entity.contactList.add(new Contact("mob", "+790000"));
        entity.contactList.add(new Contact("mob", "+795555"));
        entity.tags.add("tag1");
        entity.map.put("key1", "val1");
        entity.map.put("key2", 234);
        repository.save(entity);
        final Customer entity1 = new Customer("Bob", "Smith");
        repository.save(entity1);

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        //System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }

        System.out.println("Customers found with findByTags('tag2'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByTags("tag2")) {
            System.out.println(customer);
        }
        System.out.println("Customers found with findByTags('tag1'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByTags("tag1")) {
            System.out.println(customer);
        }

    }

}

