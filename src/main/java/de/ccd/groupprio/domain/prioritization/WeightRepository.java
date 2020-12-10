package de.ccd.groupprio.domain.prioritization;

import java.util.List;

public interface WeightRepository {

    List<WeightedItem> findForProjectId(long projectId);

    void save(long projectId, List<WeightedItem> items);
}
