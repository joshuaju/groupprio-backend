package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.util.Exchange;
import de.ccd.groupprio.api.util.JsonBody;
import de.ccd.groupprio.domain.prioritization.WeightedItem;
import de.ccd.groupprio.domain.project.Project;
import de.ccd.groupprio.domain.project.ProjectService;
import de.ccd.groupprio.domain.project.WeightedProject;
import io.undertow.server.HttpServerExchange;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    public void createProject(HttpServerExchange exchange) {
        var body = Exchange.jsonBody(exchange);
        var title = body.get("title").asText();
        var items = JsonBody.stringSet(body.get("items"));

        long id = projectService.createProject(title, items);

        Exchange.sendJson(exchange, Map.of("id", id));
    }

    public void getProject(HttpServerExchange exchange) {
        var id = Long.parseLong(Exchange.pathParam(exchange, "id"));
        Project project = projectService.getProject(id);
        Exchange.sendJson(exchange, Map.of("items", project.getItems()));
    }

    public void getProjectState(HttpServerExchange exchange) {
        var id = Long.parseLong(Exchange.pathParam(exchange, "id"));

        WeightedProject projectState = projectService.getProjectState(id);

        var items = projectState.getWeightedItems().stream()
                .map(WeightedItem::getName)
                .collect(Collectors.toList());
        Exchange.sendJson(exchange, Map.of("items", items));
    }
}
