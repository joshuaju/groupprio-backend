package de.ccd.groupprio.domain.data;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Project {

    private final String id;
    private final String title;
    private final Set<String> items;
    private final String clientId;
    private final boolean isMultipleSubmissionsAllowed;

    public Project(
              String title,
              Set<String> items,
              boolean isMultipleSubmissionsAllowed,
              String clientId) {
        this(UUID.randomUUID().toString(), title, items, isMultipleSubmissionsAllowed, clientId);
    }

    public Project(
              String id,
              String title,
              Set<String> items,
              boolean isMultipleSubmissionsAllowed,
              String clientId) {
        this.id = id;
        this.title = title;
        this.items = items;
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

    public boolean isMultipleSubmissionsAllowed() {
        return isMultipleSubmissionsAllowed;
    }

    public String getClientId() {
        return clientId;
    }
}
