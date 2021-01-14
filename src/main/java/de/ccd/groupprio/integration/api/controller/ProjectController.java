package de.ccd.groupprio.integration.api.controller;

import de.ccd.groupprio.integration.api.dto.ProjectDto;
import de.ccd.groupprio.integration.api.dto.ProjectStateDto;
import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.integration.services.ProjectService;
import de.ccd.groupprio.domain.data.WeightedProject;

import java.util.Map;

import static de.ccd.groupprio.integration.api.controller.JsonUtil.json;
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

            WeightedProject weightedProject = this.projectService.getProjectState(id);

            return new ProjectStateDto(weightedProject.getTitle(),weightedProject.getItems());
        }, json());
    }

    private void getProject() {
        get("/project/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));

            Project project = this.projectService.getProject(id);

            return new ProjectDto(project.getTitle(),project.getItems());
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