package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SubmitProcessor {

    private final SubmissionRepository submissionRepository;
    private final WeightRepository weightRepository;
    private final ProjectRepository projectRepository;

    SubmitResponse process(SubmitCommand cmd) {
        var project = projectRepository.getByProjectId(cmd.projectId);
        if (project.isClientNotAllowedToSubmit(cmd.clientId)) {
            return new SubmitResponse(false, false);
        }

        this.submit(cmd);
        this.calcPriorities(cmd.projectId);

        boolean anotherSubmissionAllowed = project.isMultiSubmissionAllowed();
        return new SubmitResponse(true, anotherSubmissionAllowed);
    }

    private void submit(SubmitCommand cmd) {
        var prioItems = PrioItem.listOf(cmd.items);
        Submission submission = new Submission(prioItems);
        submissionRepository.save(cmd.projectId, cmd.clientId, submission);
    }

    private void calcPriorities(String projectId) {
        var submissions = submissionRepository.findForProjectId(projectId);
        var weightedItems = Prioritization.averageSubmissions(submissions);
        weightRepository.save(projectId, weightedItems);
    }
}
