package ru.cwl.example.springdataredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataRedisApplication {

    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(SpringDataRedisApplication.class, args);
        PersonRepository repository = context.getBean(PersonRepository.class);
//        repository.save(new Person("Ivan", "Ivanov"));
//        repository.save(new Person("Cidre", "Sidorov"));
//
//
//        for (Person person : repository.findByFirstName("Ivan")) {
//            System.out.println(person);
//        }
//
//
//        for (Person person : repository.findByLastName("Sidorov")) {
//            System.out.println(person);
//        }

        Example ex = context.getBean(Example.class);
//        ex.syncTest();
        ex.asyncTest();

//        repository.deleteAll();
    }

}
