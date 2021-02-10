package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.List;

public interface WeightRepository {

    List<WeightedItem> findForProjectId(String projectId);

    void save(String projectId, List<WeightedItem> items);
}
