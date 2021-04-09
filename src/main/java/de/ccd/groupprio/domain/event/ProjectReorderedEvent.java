package de.ccd.groupprio.domain.event;

import de.ccd.groupprio.event_store.Event;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
public final class ProjectReorderedEvent extends Event {

    private final List<String> orderedItems;

    public ProjectReorderedEvent(long index, String contextId, List<String> orderedItems) {
        super(index, contextId, "ProjectReorderedEvent");
        this.orderedItems = orderedItems;
    }
}
