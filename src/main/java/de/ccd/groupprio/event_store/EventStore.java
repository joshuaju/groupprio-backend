package de.ccd.groupprio.event_store;

import java.util.List;
import java.util.stream.Stream;

public interface EventStore {

    void record(Event event);

    void record(Event... events);

    List<Event> replay();

    Stream<Event> replay(String contextId);

    <T extends Event> Stream<T> replay(Class<T> clazz);

    <T extends Event> Stream<T> replay(String contextId, Class<T> clazz);
}
