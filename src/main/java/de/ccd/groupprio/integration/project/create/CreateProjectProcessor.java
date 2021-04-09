package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.EventStore;
import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateProjectProcessor {

    private final EventStore eventStore;
    private final ProjectRepository projectRepository;

    public CreatedProjectResponse process(CreateProjectCommand cmd) {
        var project = new Project(cmd.title, cmd.items, cmd.multiSubmission, cmd.createdByClientId);
        var id = projectRepository.save(project);

        eventStore.record(new ProjectCreatedEvent(0, project.getId(), project.getClientId(), project.getTitle(), project.isMultiSubmissionAllowed()));
        eventStore.record(new ItemsAddedEvent(1, project.getId(), project.getItems()));
        return new CreatedProjectResponse(id);
    }
}
