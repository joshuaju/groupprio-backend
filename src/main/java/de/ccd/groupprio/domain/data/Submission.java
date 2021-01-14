package de.ccd.groupprio.domain.data;

import java.util.Collections;
import java.util.List;

public class Submission {

    private final List<PrioItem> items;

    public Submission(List<PrioItem> items) {
        this.items = items;
    }

    public List<PrioItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getItemName(int i) {
        return items.get(i).getName();
    }
}
