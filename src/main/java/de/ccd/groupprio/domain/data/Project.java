package de.ccd.groupprio.domain.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Project {

    private final String id;
    private final String title;
    private final Set<String> items;
    private final String clientId; // owner
    private final Set<String> submitterIds; //
    private final boolean isMultipleSubmissionsAllowed;

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
}
