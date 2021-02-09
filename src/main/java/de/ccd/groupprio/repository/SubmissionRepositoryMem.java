package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Submission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmissionRepositoryMem implements SubmissionRepository {
    private final Map<String, List<Submission>> submissions = new HashMap<>();

    @Override
    public List<Submission> findForProjectId(String projectId) {
        return submissions.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(String projectId, String clientId, Submission submission) {
        submissions.computeIfAbsent(projectId, id -> new ArrayList<>()).add(submission);
    }

    @Override
    public int getSubmissionCount(String projectId) {
        return findForProjectId(projectId).size();
    }

    @Override
    public boolean hasClientSubmitted(String projectId, String clientId) {
        return false;
    }
}
