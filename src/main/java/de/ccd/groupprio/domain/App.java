package de.ccd.groupprio.domain;

import de.ccd.groupprio.domain.prioritization.PrioritizationService;
import de.ccd.groupprio.domain.prioritization.WeightRepository;
import de.ccd.groupprio.domain.project.ProjectRepository;
import de.ccd.groupprio.domain.project.ProjectService;
import de.ccd.groupprio.domain.submission.SubmissionRepository;
import de.ccd.groupprio.domain.submission.SubmissionService;
import lombok.Getter;

@Getter
public class App { // TODO remove App, move code to main

    private final SubmissionService submissionService;
    private final PrioritizationService prioritizationService;
    private final ProjectService projectService;

    public App(ProjectRepository projectRepository, WeightRepository weightRepository, SubmissionRepository submissionRepository) {
        projectService = new ProjectService(projectRepository, weightRepository);
        prioritizationService = new PrioritizationService(submissionRepository, weightRepository);
        submissionService = new SubmissionService(prioritizationService, submissionRepository);
    }

}
