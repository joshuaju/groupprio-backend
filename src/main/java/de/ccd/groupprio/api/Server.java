package de.ccd.groupprio.api;

import de.ccd.groupprio.api.controller.ProjectController;
import de.ccd.groupprio.api.controller.SubmissionController;
import de.ccd.groupprio.domain.App;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;

public class Server {

    public static final int PORT = 8080;
    public static final String HOST = "0.0.0.0";

    private final ProjectController projectController;
    private final SubmissionController submissionController;

    public Server(App app) {
        this.projectController = new ProjectController(app.getProjectService());
        this.submissionController = new SubmissionController(app.getSubmissionService());
    }

    public void start() {
        Undertow.builder()
                .addHttpListener(PORT, HOST, routes())
                .build()
                .start();
    }

    private RoutingHandler routes() {
        return new RoutingHandler()
                .post("project/",
                        new BlockingHandler(projectController::createProject))
                .get("project/{id}",
                        new BlockingHandler(projectController::getProject))
                .get("project/{id}/prioritization",
                        new BlockingHandler(projectController::getProjectState))
                .get("project/{id}/items",
                        new BlockingHandler(projectController::getProject))
                .post("project/{id}/submission",
                        new BlockingHandler(submissionController::submit))
                .put("project/{id}/submission/{subId}",
                        new BlockingHandler(exchange -> { /* TODO */ }));
    }

}
