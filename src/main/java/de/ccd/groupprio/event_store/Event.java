package de.ccd.groupprio.event_store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public abstract class Event {

    @Setter
    long index;

    protected final String contextId;
    protected final String type;
    protected final Instant timestamp;

    public Event(String contextId, String type, Instant timestamp) {
        this.contextId = contextId;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Event(String contextId, String type) {
        this(contextId, type, Instant.now());
    }
}
