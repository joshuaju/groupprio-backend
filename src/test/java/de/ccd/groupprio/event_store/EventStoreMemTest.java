package de.ccd.groupprio.event_store;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.domain.event.ProjectReorderedEvent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class EventStoreMemTest {

    @Test
    void play() {

        var store = new EventStoreMem();

        { // store events ...
            var created1 = new ProjectCreatedEvent(0, "pid:abc", "jujo", "Abc", false);
            var itemsAdded1 = new ItemsAddedEvent(1, "pid:abc", Set.of("A", "B", "C"));

            store.record(created1);
            store.record(itemsAdded1);

            var created2 = new ProjectCreatedEvent(0, "pid:def", "jujo", "Abc", false);
            var itemsAdded2 = new ItemsAddedEvent(1, "pid:def", Set.of("D", "E", "F"));

            store.record(created2);
            store.record(itemsAdded2);
        }

        { // replay events into project
            var created = (ProjectCreatedEvent) store.replay().stream()
                                                     .filter(e -> e.getContextId().equals("pid:abc"))
                                                     .filter(e -> e instanceof ProjectCreatedEvent)
                                                     .findFirst().orElseThrow();
            var itemsAdded = (ItemsAddedEvent) store.replay().stream()
                                                    .filter(e -> e.getContextId().equals("pid:abc"))
                                                    .filter(e -> e instanceof ItemsAddedEvent)
                                                    .findFirst().orElseThrow();

            var project = new Project(
                      created.getTitle(),
                      itemsAdded.getItems(),
                      created.isMultiSubmission(),
                      created.getOwnerId());

            System.out.println(project);
        }

        { // submit orders to 'pid:abc'
            var idx = store.replay().stream()
                           .filter(e -> e.getContextId().equals("pid:abc"))
                           .map(Event::getIndex)
                           .max(Long::compareTo).orElseThrow();
            var suggestedOrders = store.replay().stream()
                                       .filter(e -> e.getContextId().equals("pid:abc"))
                                       .filter(e -> e instanceof ItemOrderSuggestedEvent)
                                       .map(e -> (ItemOrderSuggestedEvent) e)
                                       .collect(Collectors.toList());

            var suggestedOrder = new ItemOrderSuggestedEvent(idx + 1, "pid:abc", "orth", List.of("B", "A", "C"));
            var projectOrder = List.of("B", "A", "C"); // calculate projectOrder using 'suggestedOrders' and new 'suggestedOrder'
            var projectReordered = new ProjectReorderedEvent(idx + 2, "pid:abc", projectOrder);

            store.record(suggestedOrder);
            store.record(projectReordered);
        }

        { // replay events to get the current project order
            var reorderedEvent = store.replay().stream()
                                    .filter(e -> e.getContextId().equals("pid:abc"))
                                    .filter(e -> e instanceof ProjectReorderedEvent)
                                    .map(e -> (ProjectReorderedEvent) e)
                                    .max(Comparator.comparingLong(Event::getIndex)).orElseThrow();

            System.out.println("Project 'abc' order is: ");
            System.out.println(Arrays.toString(reorderedEvent.getOrderedItems().toArray()));
        }
    }
}