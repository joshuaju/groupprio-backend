package de.ccd.groupprio;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.ccd.groupprio.integration.App;
import de.ccd.groupprio.integration.api.controller.ProjectController;
import de.ccd.groupprio.integration.api.controller.SubmissionController;
import de.ccd.groupprio.repository.*;
import lombok.SneakyThrows;

import static spark.Spark.*;

public class Runner {

    public static void main(String[] args) {
        DB groupprioDB = connectMongoDb();
        ProjectRepository projectRepository = new ProjectRepositoryMongo(groupprioDB);
        WeightRepository weightRepository = new WeightRepositoryMongo(groupprioDB);
        SubmissionRepository submissionRepository = new SubmissionRepositoryMongo(groupprioDB);

        App app = new App(projectRepository, weightRepository, submissionRepository);

        port(8080);
        enableCORS("*", "GET,OPTIONS,POST,PUT,DELETE", "Authorization,Content-Type,Link,X-Total-Count,Range");
        new ProjectController(app.getProjectService());
        new SubmissionController(app.getSubmissionService());
        System.out.println("Running server on localhost:8080/");
    }

    @SneakyThrows
    private static DB connectMongoDb() {
        var mongoHost = System.getenv().getOrDefault("MONGO_HOST", "127.0.0.1");
        System.out.println("Connecting to mongoDB at " + mongoHost);
        ServerAddress addr = new ServerAddress(mongoHost);
        var mongoClient = new MongoClient(addr);
        return mongoClient.getDB("groupprio");
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {

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
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}