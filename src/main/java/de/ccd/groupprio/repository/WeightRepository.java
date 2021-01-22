package de.ccd.groupprio.repository;

import java.util.List;

import de.ccd.groupprio.domain.data.WeightedItem;

public interface WeightRepository {

    List<WeightedItem> findForProjectId(String projectId);

    void save(String projectId, List<WeightedItem> items);
}
