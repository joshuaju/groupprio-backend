package de.ccd.groupprio.domain.prioritization;

import java.util.TreeSet;

public interface WeightRepository {

    TreeSet<WeightedItem> findForProjectId(long projectId);

    void save(long projectId, TreeSet<WeightedItem> items);
}
