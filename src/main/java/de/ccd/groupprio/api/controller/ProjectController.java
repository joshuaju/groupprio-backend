package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.dto.ProjectDto;
import de.ccd.groupprio.domain.prioritization.WeightedItem;
import de.ccd.groupprio.domain.project.Project;
import de.ccd.groupprio.domain.project.ProjectService;
import de.ccd.groupprio.domain.project.WeightedProject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createProject(ProjectDto project) {
        long id = projectService.createProject(project.title, project.items);
        return Response.ok(id).build();

    }

    @GET
    @Path("/${id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Project getProject(@PathParam("id") long id) {
        Project project = projectService.getProject(id);
        return project;
    }

    @GET
    @Path("/${id}/prioritization")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getProjectState(@PathParam("id") long id) {
        WeightedProject projectState = projectService.getProjectState(id);

        var items = projectState.getWeightedItems().stream()
                .map(WeightedItem::getName)
                .collect(Collectors.toList());
        return items;
    }
}
