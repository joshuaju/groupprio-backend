package de.ccd.groupprio.event_store;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public abstract class Event {

    protected final long index;
    protected final String contextId;
    protected final String type;
    protected final Instant timestamp;

    public Event(long index, String contextId, String type, Instant timestamp) {
        this.index = index;
        this.contextId = contextId;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Event(long index, String contextId, String type) {
        this(index, contextId, type, Instant.now());
    }
}
