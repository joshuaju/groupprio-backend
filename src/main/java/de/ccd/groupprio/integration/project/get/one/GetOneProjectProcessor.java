package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetOneProjectProcessor {

    private final EventStore eventStore;

    OneProjectResponse process(GetOneProjectQuery qry) {
        var created = eventStore.replay(qry.projectId, ProjectCreatedEvent.class)
                                .findFirst()
                                .orElseThrow();
        var itemsAdded = eventStore.replay(qry.projectId, ItemsAddedEvent.class)
                                   .findFirst()
                                   .orElseThrow();
        var submitterIds = eventStore.replay(qry.projectId, ItemOrderSuggestedEvent.class)
                                     .map(ItemOrderSuggestedEvent::getSubmitterId)
                                     .collect(Collectors.toSet());

        var project = new Project(
                  created.getContextId(),
                  created.getTitle(),
                  itemsAdded.getItems(),
                  submitterIds,
                  created.isMultiSubmission(),
                  created.getOwnerId());

        var isSubmissionAllowed = project.isClientAllowedToSubmit(qry.clientId);
        return OneProjectResponse.from(project, isSubmissionAllowed);
    }
}
