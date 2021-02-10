package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightRepositoryMem implements WeightRepository {

    private final Map<String, List<WeightedItem>> weights = new HashMap<>();

    @Override
    public List<WeightedItem> findForProjectId(String projectId) {
        return weights.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(String projectId, List<WeightedItem> items) {
        weights.put(projectId, items);
    }
}
