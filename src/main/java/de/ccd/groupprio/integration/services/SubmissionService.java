package de.ccd.groupprio.integration.services;

import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.repository.SubmissionRepository;
import de.ccd.groupprio.repository.WeightRepository;

import java.util.List;

public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final WeightRepository weightRepository;

    public SubmissionService(SubmissionRepository submissionRepository, WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
        this.submissionRepository = submissionRepository;
    }

    public void submitWithRecalc(long projectId, List<PrioItem> items) {
        this.submit(projectId, items);
        this.calcPriorities(projectId);
    }

    private void submit(long projectId, List<PrioItem> items) {
        Submission submission = new Submission(items);
        submissionRepository.save(projectId, submission);
    }

    private void calcPriorities(long projectId) { // TODO move to SubmissionService to get rid of dependencies
        var submissions = submissionRepository.findForProjectId(projectId);
        var weightedItems = Prioritization.averageSubmissions(submissions);
        weightRepository.save(projectId, weightedItems);
    }
}
