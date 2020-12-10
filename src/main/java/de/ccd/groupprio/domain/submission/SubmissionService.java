package de.ccd.groupprio.domain.submission;

import de.ccd.groupprio.domain.prioritization.PrioritizationService;

import java.util.List;

public class SubmissionService {

    private final PrioritizationService prioritizationService;
    private final SubmissionRepository submissionRepository;

    public SubmissionService(PrioritizationService prioritizationService, SubmissionRepository submissionRepository) {
        this.prioritizationService = prioritizationService;
        this.submissionRepository = submissionRepository;
    }

    public void submitWithRecalc(long projectId, List<PrioItem> items) {
        this.submit(projectId, items);
        prioritizationService.calcPriorities(projectId);
    }

    private void submit(long projectId, List<PrioItem> items) {
        Submission submission = new Submission(items);
        submissionRepository.save(projectId, submission);
    }
}
