package de.ccd.groupprio.domain.data;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getItems() {
        return  this.weightedItems.stream()
                .map(WeightedItem::getName)
                .collect(Collectors.toList());
    }
}
