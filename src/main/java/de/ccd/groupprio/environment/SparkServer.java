package de.ccd.groupprio.environment;

import java.util.Arrays;

import static spark.Spark.*;

public class SparkServer {

    private final int port;

    private SparkServer(int port) {
        this.port = port;
        port(8080);
    }

    public static SparkServer port(int port) {
        return new SparkServer(port);
    }

    public SparkServer register(Runnable... controller) {
        Arrays.stream(controller).forEach(Runnable::run);
        return this;
    }

    public SparkServer enableCors() {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET,OPTIONS,POST,PUT,DELETE");
            response.header("Access-Control-Allow-Headers", "Authorization,Content-Type,Link,X-Total-Count,Range");
            response.type("application/json");
        });

        after((request, response) -> {
            response.type("application/json");
        });

        return this;
    }

    public void run() {
        System.out.println("Running server on localhost:" + port);
    }
}
