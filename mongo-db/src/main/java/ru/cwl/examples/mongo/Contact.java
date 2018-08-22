package ru.cwl.examples.mongo;

/**
 * Created by vadim.tishenko
 * on 15.08.2018 22:13.
 */
public class Contact {
    public String name;
    public String tel;

    public Contact() {
    }

    public Contact(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
