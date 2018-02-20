package ru.cwl.examples.springmfw;

import java.util.Objects;

import lombok.Data;

/**
 * Created by tischenko on 19.02.2018 15:19.
 */

public class Hello {
    private String name;

    public Hello() {
    }

    public Hello(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hello hello = (Hello) o;
        return Objects.equals(name, hello.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
