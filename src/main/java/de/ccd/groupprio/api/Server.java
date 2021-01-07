package de.ccd.groupprio.api;

import com.sun.net.httpserver.HttpServer;
import de.ccd.groupprio.api.controller.ProjectController;
import de.ccd.groupprio.api.controller.SubmissionController;
import de.ccd.groupprio.api.util.CorsHandler;
import de.ccd.groupprio.domain.App;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
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
                         .post("project/", wrap(projectController::createProject))
                         .get("project/{id}", wrap(projectController::getProject))
                         .get("project/{id}/prioritization", wrap(projectController::getProjectState))
                         .get("project/{id}/items", wrap(projectController::getProject))
                         .post("project/{id}/submission", wrap(submissionController::submit))
                         .put("project/{id}/submission/{subId}", wrap(exchange -> { /* TODO */ }));
    }

    private static HttpHandler wrap(HttpHandler handler) {
        return new CorsHandler(new BlockingHandler(handler));
    }
}
