package de.ccd.groupprio.domain.prioritization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class WeightRepositoryMem implements WeightRepository {

    private Map<Long, List<WeightedItem>> weights = new HashMap<>();

    @Override
    public List<WeightedItem> findForProjectId(long projectId) {
        return weights.getOrDefault(projectId, List.of());
    }

    @Override
    public void save(long projectId, List<WeightedItem> items) {
        weights.put(projectId, items);
    }
}
