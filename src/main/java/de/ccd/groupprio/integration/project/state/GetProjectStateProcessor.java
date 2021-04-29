package de.ccd.groupprio.integration.project.state;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetProjectStateProcessor {

    private final EventStore eventStore;

    ProjectStateResponse process(ProjectStateQuery qry) {
        var project = ProjectAggregate.rehydrate(eventStore.replay(qry.projectId));
        return new ProjectStateResponse(project.getTitle(), project.getOrderedItems(), project.countSubmission());
    }
}
