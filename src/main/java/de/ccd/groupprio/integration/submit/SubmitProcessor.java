package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class SubmitProcessor {

    private final SubmissionRepository submissionRepository;
    private final WeightRepository weightRepository;
    private final ProjectRepository projectRepository;

    SubmitResponse process(SubmitCommand cmd) {
        if (isNotAllowedToSubmit(cmd))
            return new SubmitResponse(false);

        this.submit(cmd);
        this.calcPriorities(cmd.projectId);
        return new SubmitResponse(true);
    }

    private boolean isNotAllowedToSubmit(SubmitCommand cmd) {
        var project = projectRepository.getByProjectId(cmd.projectId);
        var allowMultiSubmission = project.isMultipleSubmissionsAllowed();

        var alreadySubmitted = submissionRepository
                .hasClientSubmitted(cmd.projectId, cmd.clientId);

        return !(allowMultiSubmission || !alreadySubmitted);
    }

    private void submit(SubmitCommand cmd) {
        var prioItems = cmd.items.stream()
                .map(PrioItem::new)
                .collect(Collectors.toList());
        Submission submission = new Submission(prioItems);
        submissionRepository.save(cmd.projectId, cmd.clientId, submission);
    }

    private void calcPriorities(String projectId) {
        var submissions = submissionRepository.findForProjectId(projectId);
        var weightedItems = Prioritization.averageSubmissions(submissions);
        weightRepository.save(projectId, weightedItems);
    }
}
