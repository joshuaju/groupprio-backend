package de.ccd.groupprio.domain.data;

import java.util.List;
import java.util.stream.Collectors;

public class PrioItem implements Comparable<PrioItem> {

    private final String name;

    public PrioItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(PrioItem o) {
        return this.name.compareTo(o.name);
    }

    public static List<PrioItem> listOf(List<String> itemNames) {
        return itemNames.stream()
                        .map(PrioItem::new)
                        .collect(Collectors.toList());
    }
}