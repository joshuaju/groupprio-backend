package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.data.WeightedItem;
import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ProjectReorderedEvent;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.event_store.EventStore;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class SubmitProcessor {

    private final EventStore eventStore;
    private final SubmissionRepository submissionRepository;
    private final WeightRepository weightRepository;
    private final ProjectRepository projectRepository;

    SubmitResponse process(SubmitCommand cmd) {
        var project = projectRepository.getByProjectId(cmd.projectId);
        if (project.isClientNotAllowedToSubmit(cmd.clientId)) {
            return new SubmitResponse(false, false);
        }

        var itemOrderSuggested = this.submit(cmd);
        eventStore.record(itemOrderSuggested);
        var projectedReordered = this.calcPriorities(cmd.projectId);
        eventStore.record(projectedReordered);

        boolean anotherSubmissionAllowed = project.isMultiSubmissionAllowed();
        return new SubmitResponse(true, anotherSubmissionAllowed);
    }

    private ItemOrderSuggestedEvent submit(SubmitCommand cmd) {
        var prioItems = PrioItem.listOf(cmd.items);
        Submission submission = new Submission(prioItems);
        submissionRepository.save(cmd.projectId, cmd.clientId, submission);

        return new ItemOrderSuggestedEvent(cmd.projectId, cmd.clientId, cmd.items);
    }

    private ProjectReorderedEvent calcPriorities(String projectId) {
        var submissions = submissionRepository.findForProjectId(projectId);
        var weightedItems = Prioritization.averageSubmissions(submissions);
        weightRepository.save(projectId, weightedItems);

        return new ProjectReorderedEvent(projectId, weightedItems.stream().map(WeightedItem::getName).collect(Collectors.toList()));
    }
}
