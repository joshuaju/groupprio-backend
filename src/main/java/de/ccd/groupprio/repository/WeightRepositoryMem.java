package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightRepositoryMem implements WeightRepository {

    private final Map<Long, List<WeightedItem>> weights = new HashMap<>();

    @Override
    public List<WeightedItem> findForProjectId(long projectId) {
        return weights.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(long projectId, List<WeightedItem> items) {
        weights.put(projectId, items);
    }
}
