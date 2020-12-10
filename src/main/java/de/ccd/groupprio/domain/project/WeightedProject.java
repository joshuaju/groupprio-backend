package de.ccd.groupprio.domain.project;

import de.ccd.groupprio.domain.prioritization.WeightedItem;

import java.util.List;

public class WeightedProject {

    private final String title;
    private final List<WeightedItem> weightedItems;

    public WeightedProject(String title, List<WeightedItem> weightedItems) {
        this.title = title;
        this.weightedItems = weightedItems;
    }

    public String getTitle() {
        return title;
    }

    public List<WeightedItem> getWeightedItems() {
        return weightedItems;
    }
}
