package de.ccd.groupprio.event_store;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EventStoreMem implements EventStore {

    private final List<Event> events = new LinkedList<>();

    @Override
    public void record(Event event) {
        events.add(event);
    }

    @Override
    public List<Event> replay() {
        return Collections.unmodifiableList(events);
    }
}
