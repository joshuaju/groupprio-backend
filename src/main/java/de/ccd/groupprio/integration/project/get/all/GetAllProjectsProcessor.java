package de.ccd.groupprio.integration.project.get.all;

import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetAllProjectsProcessor {

    private final EventStore eventStore;

    AllProjectsResponse process(GetAllProjectsQuery qry) {
        var projects = eventStore.replay(ProjectCreatedEvent.class)
                                 .filter(e -> e.getOwnerId().equals(qry.clientId))
                                 // map to response
                                 .map(e -> new AllProjectsResponse.OverviewProject(e.getContextId(), e.getTitle()))
                                 .collect(Collectors.toList());
        return new AllProjectsResponse(projects);
    }
}
