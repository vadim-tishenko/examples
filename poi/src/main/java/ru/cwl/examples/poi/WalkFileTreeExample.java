package ru.cwl.examples.poi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by vadim.tishenko
 * on 14.07.2018 18:51.
 */
public class WalkFileTreeExample {

    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(Paths.get("c:/tmp/az"))) {
            paths.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
