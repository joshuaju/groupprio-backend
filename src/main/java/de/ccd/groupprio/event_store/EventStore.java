package de.ccd.groupprio.event_store;

import java.util.List;

public interface EventStore {

    void record(Event event);

    List<Event> replay();
}
