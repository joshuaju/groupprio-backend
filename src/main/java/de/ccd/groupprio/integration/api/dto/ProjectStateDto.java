package de.ccd.groupprio.integration.api.dto;

import java.util.List;

public class ProjectStateDto {
    public String title;
    public List<String> weightedItems;
    public int submissionCount;

    public ProjectStateDto(String title, List<String> weightedItems, int submissionCount) {
        this.title = title;
        this.weightedItems = weightedItems;
        this.submissionCount = submissionCount;
    }
}
