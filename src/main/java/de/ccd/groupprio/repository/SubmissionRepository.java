package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Submission;

import java.util.List;

public interface SubmissionRepository {

    List<Submission> findForProjectId(long projectId);

    void save(long projectId, Submission submission);
}
