package de.ccd.groupprio.api.dto;

import java.util.List;

public class ProjectStateDto {
    public String title;
    public List<String> weightedItems;

    public ProjectStateDto(String title, List<String> weightedItems) {
        this.title = title;
        this.weightedItems = weightedItems;
    }
}
