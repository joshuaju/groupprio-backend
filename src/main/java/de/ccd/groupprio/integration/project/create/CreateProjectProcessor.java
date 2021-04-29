package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class CreateProjectProcessor {

    private final EventStore eventStore;

    public CreatedProjectResponse process(CreateProjectCommand cmd) {
        assertUniqueProjectTitle(cmd);

        var project = ProjectAggregate.create(UUID.randomUUID().toString(), cmd.title, cmd.createdByClientId, cmd.multiSubmission, cmd.items);

        eventStore.record(project.getChanges());
        return new CreatedProjectResponse(project.getId());
    }

    private void assertUniqueProjectTitle(CreateProjectCommand cmd) {
        var titleAlreadyExists = eventStore.replay(ProjectCreatedEvent.class)
                                           .filter(e -> e.getOwnerId().equals(cmd.createdByClientId))
                                           .anyMatch(e -> e.getTitle().equals(cmd.title));
        if (titleAlreadyExists) {
            throw new IllegalArgumentException("Title must be unique.");
        }
    }
}
