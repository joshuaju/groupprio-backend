package de.ccd.groupprio.domain.data;

import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.domain.event.ProjectReorderedEvent;
import de.ccd.groupprio.domain.logic.Prioritization;
import de.ccd.groupprio.event_store.Event;
import lombok.ToString;

import java.util.*;
import java.util.stream.Stream;

@ToString
public class ProjectAggregate {

    private String id;
    private String title;
    private String clientId; // owner
    private boolean isMultipleSubmissionsAllowed;
    private Set<String> items;
    private final Set<String> submitterIds = new HashSet<>(); //
    private final List<Submission> submissions = new ArrayList<>();
    private final List<Event> changes = new ArrayList<>();
    private List<String> orderedItems = new ArrayList<>();

    private ProjectAggregate() {
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public boolean isMultiSubmissionAllowed() {
        return isMultipleSubmissionsAllowed;
    }

    public boolean hasSubmitted(String clientId) {
        return submitterIds.contains(clientId);
    }

    public boolean isClientAllowedToSubmit(String clientId) {
        return this.isMultipleSubmissionsAllowed || !this.hasSubmitted(clientId);
    }

    public boolean isClientNotAllowedToSubmit(String clientId) {
        return !isClientAllowedToSubmit(clientId);
    }

    public List<String> getOrderedItems() {
        return orderedItems;
    }

    public int countSubmission() {
        return submissions.size();
    }

    public List<Event> getChanges() {
        return changes;
    }

    public void suggestItemOrder(String clientId, List<String> items) {
        ItemOrderSuggestedEvent suggestedOrderEvent = new ItemOrderSuggestedEvent(this.id, clientId, items);
        apply(suggestedOrderEvent);
        changes.add(suggestedOrderEvent);

        calculateOrder();
    }

    private void calculateOrder() {
        var weightedItems = Prioritization.averageSubmissions(submissions);

        var reorderedEvent = new ProjectReorderedEvent(this.id, weightedItems);
        apply(reorderedEvent);
        changes.add(reorderedEvent);
    }

    public static ProjectAggregate create(String projectId, String title, String ownerId, boolean isMultipleSubmissionsAllowed, Set<String> items) {
        var project = new ProjectAggregate();

        var created = new ProjectCreatedEvent(projectId, ownerId, title, isMultipleSubmissionsAllowed);
        project.apply(created);
        project.changes.add(created);

        var added = new ItemsAddedEvent(projectId, items);
        project.apply(added);
        project.changes.add(added);

        return project;
    }

    public static ProjectAggregate rehydrate(Stream<Event> events) {
        var project = new ProjectAggregate();
        events.forEach(project::applyEvent);
        return project;
    }

    private void applyEvent(Event event) {
        switch (event.getType()) {
            case "ProjectCreatedEvent":
                this.apply((ProjectCreatedEvent) event);
                break;
            case "ItemsAddedEvent":
                this.apply((ItemsAddedEvent) event);
                break;
            case "ItemOrderSuggestedEvent":
                this.apply((ItemOrderSuggestedEvent) event);
                break;
            case "ProjectReorderedEvent":
                this.apply((ProjectReorderedEvent) event);
                break;
        }
    }

    private void apply(ProjectCreatedEvent event) {
        this.id = event.getContextId();
        this.title = event.getTitle();
        this.isMultipleSubmissionsAllowed = event.isMultiSubmission();
        this.clientId = event.getOwnerId();
    }

    private void apply(ItemsAddedEvent event) {
        this.items = event.getItems();
    }

    private void apply(ItemOrderSuggestedEvent event) {
        submitterIds.add(event.getSubmitterId());
        submissions.add(new Submission(PrioItem.listOf(event.getOrderedItems())));
    }

    private void apply(ProjectReorderedEvent event) {
        this.orderedItems = event.getOrderedItems();
    }
}
