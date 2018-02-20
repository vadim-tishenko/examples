package ru.cwl.examples.springmfw;

/**
 * https://github.com/alek-sys/spring-functional-microframework
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Data @NoArgsConstructor @AllArgsConstructor
class Hello2 {
    private  String name;
}

public class FunctionalWebApplication {

    static RouterFunction getRouter() {
        HandlerFunction hello = request -> ok().body(fromObject("Hello"));
        HandlerFunction helloJson= req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello("world")));
        HandlerFunction hello2Json= req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello2("world")));

        return
                route(GET("/"), hello)
                        .andRoute(GET("/json"), helloJson)
                        .andRoute(GET("/json2"), hello2Json);
    }

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {

        RouterFunction router = getRouter();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

        HttpServer
                .create("localhost", 8082)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        Thread.currentThread().join();
    }
}