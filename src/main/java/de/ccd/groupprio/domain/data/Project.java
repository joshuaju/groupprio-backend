package de.ccd.groupprio.domain.data;

import java.util.Collections;
import java.util.Set;

public class Project {

    private final String title;
    private final Set<String> items;
    private final boolean isMultipleSubmissionsAllowed;

    public Project(String title, Set<String> items, boolean isMultipleSubmissionsAllowed) {
        this.title = title;
        this.items = items;
        this.isMultipleSubmissionsAllowed = isMultipleSubmissionsAllowed;
        // TODO validation
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
}
