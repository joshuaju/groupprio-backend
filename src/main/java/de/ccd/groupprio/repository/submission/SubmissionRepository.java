package de.ccd.groupprio.repository.submission;

import de.ccd.groupprio.domain.data.Submission;

import java.util.List;

public interface SubmissionRepository {

    List<Submission> findForProjectId(String projectId);

    void save(String projectId, String clientId, Submission submission);

    int getSubmissionCount(String projectId);

    boolean hasClientSubmitted(String projectId, String clientId);

    List<String> getSubmitters(String projectId);
}
