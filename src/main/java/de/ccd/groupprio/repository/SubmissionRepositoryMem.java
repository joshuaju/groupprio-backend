package de.ccd.groupprio.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ccd.groupprio.domain.data.Submission;

public class SubmissionRepositoryMem implements SubmissionRepository {
    private final Map<String, List<Submission>> submissions = new HashMap<>();

    @Override
    public List<Submission> findForProjectId(String projectId) {
        return submissions.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(String projectId, Submission submission) {
        submissions.computeIfAbsent(projectId, id -> new ArrayList<>()).add(submission);
    }

    @Override
    public int getSubmissionCount(long projectId) {
        return findForProjectId(projectId).size();
    }
}
