package de.ccd.groupprio.integration.api.controller;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.data.WeightedProject;
import de.ccd.groupprio.integration.api.dto.ProjectDto;
import de.ccd.groupprio.integration.api.dto.ProjectStateDto;
import de.ccd.groupprio.integration.services.ProjectService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.ccd.groupprio.integration.api.controller.JsonUtil.*;
import static spark.Spark.*;

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        createProject();
        getProject();
        getProjectState();
        getProjects();
    }

    private void getProjectState() {
        get("/project/:id/prioritization", (req, res) -> {
            String id = req.params(":id");

            WeightedProject weightedProject = this.projectService.getProjectState(id);

            return new ProjectStateDto(weightedProject.getTitle(), weightedProject.getItems(), weightedProject.getSubmissionCount());
        }, json());
    }

    private void getProject() {
        get("/project/:id", (req, res) -> {
            String id = req.params(":id");

            Project project = this.projectService.getProject(id);

            return new ProjectDto(project.getId(), project.getTitle(), project.getItems(), project.isMultipleSubmissionsAllowed());
        }, json());
    }

    private void createProject() {
        post("/project", (req, res) -> {
            ProjectDto projectDto = JsonUtil.fromJson(req.body(), ProjectDto.class);
            final var clientId = req.headers("clientId");

            String id = this.projectService.createProject(
                      projectDto.title,
                      projectDto.items,
                      projectDto.isMultipleSubmissionsAllowed,
                      clientId);

            return Map.of("id", id);
        }, json());
    }

    private void getProjects() {
        get("/project", (req, res) -> {
            final var clientId = req.headers("clientId");

            List<Project> projects = projectService.getProjects(clientId);

            return projects.stream().map(p -> new ProjectDto(p.getId(), p.getTitle(), p.getItems(), p.isMultipleSubmissionsAllowed()))
                           .collect(Collectors.toList());
        }, json());
    }
}
