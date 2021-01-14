package de.ccd.groupprio.integration.services;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.data.WeightedProject;
import de.ccd.groupprio.repository.ProjectRepository;
import de.ccd.groupprio.repository.WeightRepository;

import java.util.Set;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WeightRepository weightRepository;

    public ProjectService(ProjectRepository projectRepository, WeightRepository weightRepository) {
        this.projectRepository = projectRepository;
        this.weightRepository = weightRepository;
    }

    public long createProject(String title, Set<String> items) {
        var project = new Project(title, items);
        return projectRepository.save(project);
    }

    public Project getProject(long projectId) {
        return projectRepository.get(projectId);
    }

    public WeightedProject getProjectState(long projectId) {
        var project = projectRepository.get(projectId);
        var weightedItems = weightRepository.findForProjectId(projectId);
        return new WeightedProject(project.getTitle(), weightedItems);
    }
}
