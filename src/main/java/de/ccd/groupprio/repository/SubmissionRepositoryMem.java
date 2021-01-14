package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Submission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmissionRepositoryMem implements SubmissionRepository {

    private final Map<Long, List<Submission>> submissions = new HashMap<>();

    @Override
    public List<Submission> findForProjectId(long projectId) {
        return submissions.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(long projectId, Submission submission) {
        submissions.computeIfAbsent(projectId, id -> new ArrayList<>())
                .add(submission);
    }
}
