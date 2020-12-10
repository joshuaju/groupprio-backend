package de.ccd.groupprio.domain.submission;

import java.util.List;

public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public void submit(long projectId, List<PrioItem> items) {
        Submission submission = new Submission(items);
        submissionRepository.save(projectId, submission);
    }
}
