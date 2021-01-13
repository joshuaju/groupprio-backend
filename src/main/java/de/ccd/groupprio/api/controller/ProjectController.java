package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.dto.ProjectDto;
import de.ccd.groupprio.api.dto.ProjectStateDto;
import de.ccd.groupprio.domain.prioritization.WeightedItem;
import de.ccd.groupprio.domain.project.Project;
import de.ccd.groupprio.domain.project.ProjectService;
import de.ccd.groupprio.domain.project.WeightedProject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.ccd.groupprio.api.controller.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.post;

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        createProject();
        getProject();
        getProjectState();
    }

    private void getProjectState() {
        get("/project/:id/prioritization", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            WeightedProject projectState = this.projectService.getProjectState(id);

            List<String> weightedItems = projectState.getWeightedItems().stream()
                    .map(WeightedItem::getName)
                    .collect(Collectors.toList());
            return new ProjectStateDto(projectState.getTitle(),weightedItems);
        }, json());
    }

    private void getProject() {
        get("/project/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Project project = this.projectService.getProject(id);
            return project;
        }, json());
    }

    private void createProject() {
        post("/project", (req, res) -> {
            ProjectDto projectDto = JsonUtil.fromJson(req.body(), ProjectDto.class);
            long id = this.projectService.createProject(projectDto.title, projectDto.items);
            return Map.of("id", id);
        }, json());
    }
}
