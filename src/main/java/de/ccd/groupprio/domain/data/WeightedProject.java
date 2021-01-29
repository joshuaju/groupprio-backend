package de.ccd.groupprio.domain.data;

import java.util.List;
import java.util.stream.Collectors;

public class WeightedProject {

    private final String title;
    private final List<WeightedItem> weightedItems;
    private final int submissionCount;

    public WeightedProject(String title, List<WeightedItem> weightedItems, int submissionCount) {
        this.title = title;
        this.weightedItems = weightedItems;
        this.submissionCount = submissionCount;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getItems() {
        return  this.weightedItems.stream()
                .map(WeightedItem::getName)
                .collect(Collectors.toList());
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

}
