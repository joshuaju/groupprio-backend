package de.ccd.groupprio.repository.weight;

import com.mongodb.BasicDBList;

import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

public class WeightMapperMongo {

    private WeightMapperMongo() {
    }

    public static List<WeightedItem> mapToWeightedItemList(Document object) {
        if (object == null){
          return Collections.emptyList();
        }
        BasicDBList weightedItems = (BasicDBList) object.get("weighted_items");
        return weightedItems.stream().map(itm -> new WeightedItem(itm.toString())).collect(Collectors.toList());
    }

    public static BasicDBList mapToBasicDBList(List<WeightedItem> items) {
        BasicDBList weightedList = new BasicDBList();
        for (int i = 0; i < items.size(); i++) {
            weightedList.put(i, items.get(i).getName());
        }
        return weightedList;
    }
}
