package de.ccd.groupprio;

import de.ccd.groupprio.api.ResteasyServer;
import de.ccd.groupprio.domain.App;
import de.ccd.groupprio.domain.prioritization.WeightRepository;
import de.ccd.groupprio.domain.prioritization.WeightRepositoryMem;
import de.ccd.groupprio.domain.project.ProjectRepository;
import de.ccd.groupprio.domain.project.ProjectRepositoryMem;
import de.ccd.groupprio.domain.submission.SubmissionRepository;
import de.ccd.groupprio.domain.submission.SubmissionRepositoryMem;

public class Runner {

    public static void main(String[] args) {
        ProjectRepository projectRepository = new ProjectRepositoryMem();
        WeightRepository weightRepository = new WeightRepositoryMem();
        SubmissionRepository submissionRepository = new SubmissionRepositoryMem();
        new App(projectRepository, weightRepository, submissionRepository);
    }
}
