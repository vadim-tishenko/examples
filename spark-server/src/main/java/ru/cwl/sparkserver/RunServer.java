package ru.cwl.sparkserver;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * Created by vadim.tishenko
 * on 17.02.2018 20:15.
 */
public class RunServer {
    public static void main(String[] args) {
        staticFiles.externalLocation("sparkserver\\src\\main\\resources\\public");
        get("/hello", (req, res) -> "Hello World");
        System.out.println("http://localhost:4567/hello");
    }
}
