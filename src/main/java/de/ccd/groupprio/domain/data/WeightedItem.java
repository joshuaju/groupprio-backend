package de.ccd.groupprio.domain.data;

import java.util.Objects;

public class WeightedItem {

    private final String name;

    public WeightedItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeightedItem that = (WeightedItem) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "WeightedItem{" +
                         "name='" + name + '\'' +
                         '}';
    }
}
