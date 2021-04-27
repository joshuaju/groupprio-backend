package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.domain.event.ProjectReorderedEvent;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class SubmitProcessor {

    private final EventStore eventStore;

    SubmitResponse process(SubmitCommand cmd) {
        var created = eventStore.replay(cmd.projectId, ProjectCreatedEvent.class)
                                .findFirst()
                                .orElseThrow();
        var itemsAdded = eventStore.replay(cmd.projectId, ItemsAddedEvent.class)
                                   .findFirst()
                                   .orElseThrow();
        var submitterIds = eventStore.replay(cmd.projectId, ItemOrderSuggestedEvent.class)
                                     .map(ItemOrderSuggestedEvent::getSubmitterId)
                                     .collect(Collectors.toSet());
        var project = new Project(
                  created.getContextId(),
                  created.getTitle(),
                  itemsAdded.getItems(),
                  submitterIds,
                  created.isMultiSubmission(),
                  created.getOwnerId());

        if (project.isClientNotAllowedToSubmit(cmd.clientId)) {
            return new SubmitResponse(false, false);
        }

        eventStore.record(new ItemOrderSuggestedEvent(cmd.projectId, cmd.clientId, cmd.items));

        var projectedReordered = this.calcPriorities(cmd.projectId);
        eventStore.record(projectedReordered);

        boolean anotherSubmissionAllowed = project.isMultiSubmissionAllowed();
        return new SubmitResponse(true, anotherSubmissionAllowed);
    }

    private ProjectReorderedEvent calcPriorities(String projectId) {
        List<Submission> submissions = eventStore.replay(projectId, ItemOrderSuggestedEvent.class)
                                                 .map(e -> PrioItem.listOf(e.getOrderedItems()))
                                                 .map(Submission::new)
                                                 .collect(Collectors.toList());

        var weightedItems = Prioritization.averageSubmissions(submissions);

        return new ProjectReorderedEvent(projectId, weightedItems);
    }
}
