package de.ccd.groupprio.repository.weight;

import de.ccd.groupprio.domain.data.WeightedItem;
import org.bson.Document;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WeightMapperMongo {

    private WeightMapperMongo() {
    }

    public static List<WeightedItem> mapToWeightedItemList(Document weightDoc) {
        if (weightDoc == null) {
            return Collections.emptyList();
        }
        var weightedItemNames = weightDoc.getList("weighted_items", String.class);
        return weightedItemNames.stream()
                                .map(WeightedItem::new)
                                .collect(Collectors.toList());
    }
}
