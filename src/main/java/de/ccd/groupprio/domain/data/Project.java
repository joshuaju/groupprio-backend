package de.ccd.groupprio.domain.data;

import de.ccd.groupprio.domain.event.ItemOrderSuggestedEvent;
import de.ccd.groupprio.domain.event.ItemsAddedEvent;
import de.ccd.groupprio.domain.event.ProjectCreatedEvent;
import de.ccd.groupprio.event_store.Event;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@ToString
public class Project {

    private String id;
    private String title;
    private Set<String> items;
    private String clientId; // owner
    private Set<String> submitterIds; //
    private boolean isMultipleSubmissionsAllowed;

    private Project() {

    }

    public Project(
              String title,
              Set<String> items,
              boolean isMultipleSubmissionsAllowed,
              String clientId) {
        this(UUID.randomUUID().toString(), title, items, new HashSet<>(), isMultipleSubmissionsAllowed, clientId);
    }

    public Project(
              String id,
              String title,
              Set<String> items,
              Set<String> submitterIds,
              boolean isMultipleSubmissionsAllowed,
              String clientId) {
        this.id = id;
        this.title = title;
        this.items = items;
        this.submitterIds = submitterIds;
        this.isMultipleSubmissionsAllowed = isMultipleSubmissionsAllowed;
        this.clientId = clientId;
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

    public String getClientId() {
        return clientId;
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

    public static Project rehydrate(Stream<Event> events) {
        var project = new Project();
        Set<String> submitterIds = new HashSet<>();
        events.forEach((event) -> {
            switch (event.getType()) {
                case "ProjectCreatedEvent":
                    var created = (ProjectCreatedEvent) event;
                    project.id = created.getContextId();
                    project.title = created.getTitle();
                    project.isMultipleSubmissionsAllowed = created.isMultiSubmission();
                    project.clientId = created.getOwnerId();
                    break;
                case "ItemsAddedEvent":
                    var added = (ItemsAddedEvent) event;
                    project.items = added.getItems();
                    break;
                case "ItemOrderSuggestedEvent":
                    var suggested = (ItemOrderSuggestedEvent) event;
                    submitterIds.add(suggested.getSubmitterId());
                    break;
            }
        });
        project.submitterIds = submitterIds;
        return project;
    }
}
