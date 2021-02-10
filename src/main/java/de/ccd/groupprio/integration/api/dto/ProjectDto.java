package de.ccd.groupprio.integration.api.dto;

import java.util.Set;

public class ProjectDto {

    public String id;
    public String title;
    public Set<String> items;
    public boolean isMultipleSubmissionsAllowed;

    public ProjectDto(String id, String title, Set<String> items, boolean isMultipleSubmissionsAllowed) {
        this.id = id;
        this.title = title;
        this.items = items;
        this.isMultipleSubmissionsAllowed = isMultipleSubmissionsAllowed;
    }
}
