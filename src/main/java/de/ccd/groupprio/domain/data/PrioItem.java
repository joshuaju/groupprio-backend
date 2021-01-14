package de.ccd.groupprio.domain.data;

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
}