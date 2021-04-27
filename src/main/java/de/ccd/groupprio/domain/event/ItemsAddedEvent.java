package de.ccd.groupprio.domain.event;

import de.ccd.groupprio.event_store.Event;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString(callSuper = true)
public final class ItemsAddedEvent extends Event {

    private final Set<String> items;

    public ItemsAddedEvent(String projectId, Set<String> items) {
        super(projectId, "ItemsAddedEvent");
        this.items = items;
    }
}
