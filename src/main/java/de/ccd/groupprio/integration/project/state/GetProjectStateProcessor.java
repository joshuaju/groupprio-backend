package de.ccd.groupprio.integration.project.state;

import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.domain.event.ProjectReorderedEvent;
import de.ccd.groupprio.event_store.Event;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;

@RequiredArgsConstructor
class GetProjectStateProcessor {

    private final EventStore eventStore;

    ProjectStateResponse process(ProjectStateQuery qry) {
        var title = eventStore.replay(qry.projectId, ProjectCreatedEvent.class)
                              .findFirst()
                              .orElseThrow()
                              .getTitle();

        var orderedItems = eventStore.replay(qry.projectId, ProjectReorderedEvent.class)
                                     .max(Comparator.comparingLong(Event::getIndex))
                                     .map(ProjectReorderedEvent::getOrderedItems)
                                     .orElse(Collections.emptyList());

        var submissionCount = eventStore.replay(qry.projectId, ItemOrderSuggestedEvent.class)
                                        .count();

        return new ProjectStateResponse(title, orderedItems, submissionCount);
    }
}
