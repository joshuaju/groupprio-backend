package de.ccd.groupprio.domain.submission;

import java.util.List;

public interface SubmissionRepository {

    List<Submission> findForProjectId(long projectId);

    void save(long projectId, Submission submission);
}
