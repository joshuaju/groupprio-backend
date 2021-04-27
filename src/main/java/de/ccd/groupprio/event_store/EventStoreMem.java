package de.ccd.groupprio.event_store;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EventStoreMem implements EventStore {

    private final AtomicInteger eventIndex = new AtomicInteger(0);
    private final List<Event> events = new LinkedList<>();

    @Override
    public void record(Event event) {
        var nextEventIndex = eventIndex.addAndGet(1);
        event.setIndex(nextEventIndex);
        events.add(event);
    }

    @Override
    public List<Event> replay() {
        return Collections.unmodifiableList(events);
    }
}
