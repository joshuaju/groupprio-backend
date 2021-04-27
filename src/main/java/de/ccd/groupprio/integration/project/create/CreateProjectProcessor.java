package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateProjectProcessor {

    private final EventStore eventStore;

    public CreatedProjectResponse process(CreateProjectCommand cmd) {
        var project = new Project(cmd.title, cmd.items, cmd.multiSubmission, cmd.createdByClientId);

        // TODO check Title is unique for user

        eventStore.record(new ProjectCreatedEvent(project.getId(), project.getClientId(), project.getTitle(), project.isMultiSubmissionAllowed()),
                          new ItemsAddedEvent(project.getId(), project.getItems()));

        return new CreatedProjectResponse(project.getId());
    }
}
