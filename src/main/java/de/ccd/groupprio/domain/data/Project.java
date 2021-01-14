package de.ccd.groupprio.domain.data;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Project {

    private final String title;
    private final Set<String> items;

    public Project(String title, Set<String> items) {
        this.title = title;
        this.items = items;
        // TODO validation
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getItems() {
        return Collections.unmodifiableSet(items);
    }
}