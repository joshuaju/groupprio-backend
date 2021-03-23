package de.ccd.groupprio;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.port;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import de.ccd.groupprio.integration.project.create.CreateProjectController;
import de.ccd.groupprio.integration.project.get.GetAllProjectsController;
import de.ccd.groupprio.integration.project.get.GetOneProjectController;
import de.ccd.groupprio.integration.project.state.GetProjectStateController;
import de.ccd.groupprio.integration.submit.SubmitController;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.project.ProjectRepositoryMongo;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepositoryMongo;
import de.ccd.groupprio.repository.weight.WeightRepository;
import de.ccd.groupprio.repository.weight.WeightRepositoryMongo;
import lombok.SneakyThrows;

public class Runner {

    public static void main(String[] args) {
        MongoDatabase groupprioDB = connectMongoDb();
        ProjectRepository projectRepository = new ProjectRepositoryMongo(groupprioDB);
        WeightRepository weightRepository = new WeightRepositoryMongo(groupprioDB);
        SubmissionRepository submissionRepository = new SubmissionRepositoryMongo(groupprioDB);

        port(8080);
        enableCORS("*", "GET,OPTIONS,POST,PUT,DELETE", "Authorization,Content-Type,Link,X-Total-Count,Range");
        new CreateProjectController(projectRepository);
        new GetOneProjectController(projectRepository, submissionRepository);
        new GetAllProjectsController(projectRepository);
        new GetProjectStateController(projectRepository, weightRepository, submissionRepository);
        new SubmitController(submissionRepository, weightRepository, projectRepository);
        System.out.println("Running server on localhost:8080/");
    }

    @SneakyThrows
    private static MongoDatabase connectMongoDb() {
        var mongoHost = System.getenv().getOrDefault("MONGO_HOST", "127.0.0.1");
        System.out.println("Connecting to mongoDB at " + mongoHost);
        ServerAddress addr = new ServerAddress(mongoHost);
        var mongoClient = new MongoClient(addr);
        return mongoClient.getDatabase("groupprio");
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
