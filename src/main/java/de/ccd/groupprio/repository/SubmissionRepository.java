package de.ccd.groupprio.repository;

import java.util.List;

import de.ccd.groupprio.domain.data.Submission;

public interface SubmissionRepository {

    List<Submission> findForProjectId(String projectId);

    void save(String projectId, Submission submission);

    int getSubmissionCount(long projectId);
}
