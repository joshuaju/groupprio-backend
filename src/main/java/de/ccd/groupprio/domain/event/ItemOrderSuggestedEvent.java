package de.ccd.groupprio.domain.event;

import de.ccd.groupprio.event_store.Event;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
public final class ItemOrderSuggestedEvent extends Event {

    private final String submitterId;
    private final List<String> orderedItems;

    public ItemOrderSuggestedEvent(String projectId, String submitterId, List<String> orderedItems) {
        super(projectId, "ItemOrderSuggestedEvent");
        this.submitterId = submitterId;
        this.orderedItems = orderedItems;
    }
}
