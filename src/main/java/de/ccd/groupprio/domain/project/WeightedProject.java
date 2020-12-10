package de.ccd.groupprio.domain.project;

import de.ccd.groupprio.domain.prioritization.WeightedItem;

import java.util.TreeSet;

public class WeightedProject {

    private final String title;
    private final TreeSet<WeightedItem> weightedItems;

    public WeightedProject(String title, TreeSet<WeightedItem> weightedItems) {
        this.title = title;
        this.weightedItems = weightedItems;
    }

    public String getTitle() {
        return title;
    }

    public TreeSet<WeightedItem> getWeightedItems() {
        return weightedItems;
    }
}
