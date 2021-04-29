package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetOneProjectProcessor {

    private final EventStore eventStore;

    OneProjectResponse process(GetOneProjectQuery qry) {
        var project = ProjectAggregate.rehydrate(eventStore.replay(qry.projectId));
        var isSubmissionAllowed = project.isClientAllowedToSubmit(qry.clientId);
        return OneProjectResponse.from(project, isSubmissionAllowed);
    }
}
