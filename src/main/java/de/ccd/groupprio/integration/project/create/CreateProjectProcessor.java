package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class CreateProjectProcessor {

    private final EventStore eventStore;

    public CreatedProjectResponse process(CreateProjectCommand cmd) {
        var project = ProjectAggregate.create(UUID.randomUUID().toString(), cmd.title, cmd.createdByClientId, cmd.multiSubmission, cmd.items);

        // TODO check Title is unique for user

        eventStore.record(project.getChanges());
        return new CreatedProjectResponse(project.getId());
    }
}
