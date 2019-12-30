package ru.cwl.sparkserver;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * Created by vadim.tishenko
 * on 17.02.2018 20:15.
 */
public class RunServer {
    public static void main(String[] args) {
//        staticFiles.externalLocation("sparkserver\\src\\main\\resources\\public");
        // root is 'src/main/resources', so put files in 'src/main/resources/public'
        staticFiles.location("/public"); // Static files
        get("/hello", (req, res) -> "Hello World");
        System.out.println("http://localhost:4567/hello");
    }
}
