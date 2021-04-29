package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import de.ccd.groupprio.event_store.EventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SubmitProcessor {

    private final EventStore eventStore;

    SubmitResponse process(SubmitCommand cmd) {
        var project = ProjectAggregate.rehydrate(eventStore.replay(cmd.projectId));
        if (project.isClientNotAllowedToSubmit(cmd.clientId)) {
            return new SubmitResponse(false, false);
        }

        project.suggestItemOrder(cmd.clientId, cmd.items);
        eventStore.record(project.getChanges());

        boolean anotherSubmissionAllowed = project.isMultiSubmissionAllowed();
        return new SubmitResponse(true, anotherSubmissionAllowed);
    }
}
