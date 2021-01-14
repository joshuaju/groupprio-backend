package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.List;

public interface WeightRepository {

    List<WeightedItem> findForProjectId(long projectId);

    void save(long projectId, List<WeightedItem> items);
}
