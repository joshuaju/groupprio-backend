package de.ccd.groupprio.domain.submission;

import java.util.TreeSet;

public class Submission {

    private final TreeSet<PrioItem> items;

    public Submission(TreeSet<PrioItem> items) {
        this.items = items;
    }

    public TreeSet<PrioItem> getItems() {
        return items;
    }
}
