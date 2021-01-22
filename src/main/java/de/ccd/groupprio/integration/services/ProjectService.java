package de.ccd.groupprio.integration.services;

import java.util.Set;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.data.WeightedProject;
import de.ccd.groupprio.repository.ProjectRepository;
import de.ccd.groupprio.repository.WeightRepository;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WeightRepository weightRepository;

    public ProjectService(ProjectRepository projectRepository, WeightRepository weightRepository) {
        this.projectRepository = projectRepository;
        this.weightRepository = weightRepository;
    }

    public String createProject(String title, Set<String> items) {
        var project = new Project(title, items);
        return projectRepository.save(project);
    }

    public Project getProject(String projectId) {
        return projectRepository.get(projectId);
    }

    public WeightedProject getProjectState(String projectId) {
        var project = projectRepository.get(projectId);
        var weightedItems = weightRepository.findForProjectId(projectId);
        return new WeightedProject(project.getTitle(), weightedItems);
    }
}
