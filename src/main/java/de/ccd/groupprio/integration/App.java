package de.ccd.groupprio.integration;

import de.ccd.groupprio.integration.services.ProjectService;
import de.ccd.groupprio.integration.services.SubmissionService;
import de.ccd.groupprio.repository.ProjectRepository;
import de.ccd.groupprio.repository.SubmissionRepository;
import de.ccd.groupprio.repository.WeightRepository;
import lombok.Getter;

@Getter
public class App { // TODO remove App, move code to main

    private final SubmissionService submissionService;
    private final ProjectService projectService;

    public App(ProjectRepository projectRepository, WeightRepository weightRepository, SubmissionRepository submissionRepository) {
        projectService = new ProjectService(projectRepository, weightRepository, submissionRepository);
        submissionService = new SubmissionService(submissionRepository, weightRepository, projectRepository);
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public SubmissionService getSubmissionService() {
        return submissionService;
    }
}
