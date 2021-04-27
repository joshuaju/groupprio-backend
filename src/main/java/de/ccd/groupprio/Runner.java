package de.ccd.groupprio;

import de.ccd.groupprio.event_store.EventStore;
import de.ccd.groupprio.event_store.EventStoreMem;
import de.ccd.groupprio.integration.project.create.CreateProjectController;
import de.ccd.groupprio.integration.project.get.all.GetAllProjectsController;
import de.ccd.groupprio.integration.project.get.one.GetOneProjectController;
import de.ccd.groupprio.integration.project.state.GetProjectStateController;
import de.ccd.groupprio.integration.submit.SubmitController;

import static spark.Spark.*;

public class Runner {

    public static void main(String[] args) {
        EventStore eventStore = new EventStoreMem();

        initExceptionHandler(Throwable::printStackTrace);
        port(8080);
        enableCORS("*", "GET,OPTIONS,POST,PUT,DELETE", "Authorization,Content-Type,Link,X-Total-Count,Range");

        new CreateProjectController(eventStore);
        new GetOneProjectController(eventStore);
        new GetAllProjectsController(eventStore);
        new GetProjectStateController(eventStore);
        new SubmitController(eventStore);
        System.out.println("Running server on localhost:8080/");
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
