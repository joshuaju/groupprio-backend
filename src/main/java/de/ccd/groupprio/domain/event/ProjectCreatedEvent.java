package de.ccd.groupprio.domain.event;

import de.ccd.groupprio.event_store.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public final class ProjectCreatedEvent extends Event {

    private final String ownerId;
    private final String title;
    private final boolean multiSubmission;

    public ProjectCreatedEvent(long index, String projectId, String ownerId, String title, boolean multiSubmission) {
        super(index, projectId, "ProjectCreatedEvent");
        this.ownerId = ownerId;
        this.title = title;
        this.multiSubmission = multiSubmission;
    }
}
