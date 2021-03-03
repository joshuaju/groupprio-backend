package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.domain.logic.IsSubmissionAllowed;
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
        var project = projectRepository.getByProjectId(cmd.projectId);
        if (isNotAllowedToSubmit(cmd, project))
            return new SubmitResponse(false,false);

        this.submit(cmd);
        this.calcPriorities(cmd.projectId);

        boolean submissionAllowed = IsSubmissionAllowed.check(project, true);
        return new SubmitResponse(true, submissionAllowed);
    }

    private boolean isNotAllowedToSubmit(SubmitCommand cmd, Project project) {
        var alreadySubmitted = submissionRepository.hasClientSubmitted(cmd.projectId, cmd.clientId);
        return !(IsSubmissionAllowed.check(project, alreadySubmitted));
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
