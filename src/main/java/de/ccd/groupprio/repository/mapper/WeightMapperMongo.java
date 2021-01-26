package de.ccd.groupprio.repository.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import de.ccd.groupprio.domain.data.WeightedItem;

public class WeightMapperMongo {

    private WeightMapperMongo() {
    }

    public static List<WeightedItem> mapToWeightedItemList(DBObject object) {
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
