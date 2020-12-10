package de.ccd.groupprio.domain.submission;

import java.util.Collection;

public interface SubmissionRepository {

    Collection<Submission> findForProjectId(long projectId);

    void save(long projectId, Submission submission);
}
