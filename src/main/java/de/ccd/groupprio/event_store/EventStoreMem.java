package de.ccd.groupprio.event_store;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class EventStoreMem implements EventStore {

    private final AtomicInteger eventIndex = new AtomicInteger(0);
    private final List<Event> events = new LinkedList<>();
    private final ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();

    @Override
    public void record(Event event) {
        try {
            writeLock.lock();
            var nextEventIndex = eventIndex.addAndGet(1);
            event.setIndex(nextEventIndex);
            events.add(event);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void record(Event... events) {
        record(Arrays.asList(events));
    }

    @Override
    public void record(List<Event> events) {
        try {
            writeLock.lock();
            for (Event e : events) {
                record(e);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Event> replay() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public Stream<Event> replay(String contextId) {
        return replay().stream()
                       .filter(e -> e.getContextId().equals(contextId));
    }

    @Override
    public <T extends Event> Stream<T> replay(Class<T> clazz) {
        return replay().stream()
                       .filter(e -> clazz.isAssignableFrom(e.getClass()))
                       .map(clazz::cast);
    }

    @Override
    public <T extends Event> Stream<T> replay(String contextId, Class<T> clazz) {
        return replay().stream()
                       .filter(e -> e.getContextId().equals(contextId))
                       .filter(e -> clazz.isAssignableFrom(e.getClass()))
                       .map(clazz::cast);
    }
}
