package de.ccd.groupprio.integration.services;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.repository.ProjectRepository;
import de.ccd.groupprio.repository.SubmissionRepository;
import de.ccd.groupprio.repository.WeightRepository;

import java.util.List;

public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final WeightRepository weightRepository;
    private final ProjectRepository projectRepository;

    public SubmissionService(SubmissionRepository submissionRepository, WeightRepository weightRepository, ProjectRepository projectRepository) {
        this.weightRepository = weightRepository;
        this.submissionRepository = submissionRepository;
        this.projectRepository = projectRepository;
    }

    public boolean submitWithRecalc(String projectId, String clientId, List<PrioItem> items) {
        if (isNotAllowedToSubmit(projectId, clientId))
            return false;

        this.submit(projectId, clientId, items);
        this.calcPriorities(projectId);
        return true;
    }

    private boolean isNotAllowedToSubmit(String projectId, String clientId) {
        var allowMultiSubmission = projectRepository.get(projectId).isMultipleSubmissionsAllowed();
        var alreadySubmitted = submissionRepository.hasClientSubmitted(projectId, clientId);
        return !(allowMultiSubmission || !alreadySubmitted);
    }

    private void submit(String projectId, String clientId, List<PrioItem> items) {
        Submission submission = new Submission(items);
        submissionRepository.save(projectId, clientId, submission);
    }

    private void calcPriorities(String projectId) {
        var submissions = submissionRepository.findForProjectId(projectId);
        var weightedItems = Prioritization.averageSubmissions(submissions);
        weightRepository.save(projectId, weightedItems);
    }
}
