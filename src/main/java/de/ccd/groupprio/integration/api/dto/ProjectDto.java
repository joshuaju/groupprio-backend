package de.ccd.groupprio.integration.api.dto;

import java.util.Set;

public class ProjectDto {
    public String title;
    public Set<String> items;
    public boolean isMultipleSubmissionsAllowed;

    public ProjectDto(String title, Set<String> items, boolean isMultipleSubmissionsAllowed) {
        this.title = title;
        this.items = items;
        this.isMultipleSubmissionsAllowed= isMultipleSubmissionsAllowed;
    }
}
