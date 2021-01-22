package de.ccd.groupprio;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.options;

import de.ccd.groupprio.integration.App;
import de.ccd.groupprio.integration.api.controller.ProjectController;
import de.ccd.groupprio.integration.api.controller.SubmissionController;
import de.ccd.groupprio.repository.ProjectRepository;
import de.ccd.groupprio.repository.ProjectRepositoryMem;
import de.ccd.groupprio.repository.SubmissionRepository;
import de.ccd.groupprio.repository.SubmissionRepositoryMem;
import de.ccd.groupprio.repository.WeightRepository;
import de.ccd.groupprio.repository.WeightRepositoryMem;

public class Runner {

    public static void main(String[] args) {
        ProjectRepository projectRepository = new ProjectRepositoryMem();
        WeightRepository weightRepository = new WeightRepositoryMem();
        SubmissionRepository submissionRepository = new SubmissionRepositoryMem();

        App app = new App(projectRepository, weightRepository, submissionRepository);

        enableCORS("*", "GET,OPTIONS,POST,PUT,DELETE", "Authorization,Content-Type,Link,X-Total-Count,Range");

        new ProjectController(app.getProjectService());
        new SubmissionController(app.getSubmissionService());
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