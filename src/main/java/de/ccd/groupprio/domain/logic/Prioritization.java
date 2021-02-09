package de.ccd.groupprio.domain.logic;

import de.ccd.groupprio.domain.data.Submission;
import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Prioritization {

    public static List<WeightedItem> averageSubmissions(List<Submission> submissions) {
        var weights = getWeights(submissions);
        var summedWeights = getSummedWeights(weights);
        return sortByWeight(summedWeights);
    }

    private static List<WeightedItem> sortByWeight(Map<String, Integer> weightPerItem) {
        return weightPerItem.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue()) // TODO and explicitly sort by key (name)
                            .map(Map.Entry::getKey)
                            .map(WeightedItem::new)
                            .collect(Collectors.toList());
    }

    private static Map<String, Integer> getSummedWeights(Map<String, List<Integer>> weightsPerItem) {
        Map<String, Integer> summedWeights = new HashMap<>();
        for (var itemWithWeights : weightsPerItem.entrySet()) {
            var itemName = itemWithWeights.getKey();
            var weightSum = itemWithWeights.getValue().stream()
                                           .reduce(Integer::sum)
                                           .orElse(0);
            summedWeights.put(itemName, weightSum);
        }
        return summedWeights;
    }

    private static Map<String, List<Integer>> getWeights(List<Submission> submissions) {
        Map<String, List<Integer>> weights = new HashMap<>();
        for (var submission : submissions) {
            for (int i = 0; i < submission.getItems().size(); i++) {
                String itemName = submission.getItemName(i);
                Integer weight = i + 1;

                List<Integer> weightsForItem = weights.computeIfAbsent(itemName, s -> new ArrayList<>());
                weightsForItem.add(weight);
            }
        }
        return weights;
    }
}
