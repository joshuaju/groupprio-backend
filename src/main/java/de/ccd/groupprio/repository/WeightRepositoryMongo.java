package de.ccd.groupprio.repository;

import com.mongodb.*;
import de.ccd.groupprio.domain.data.WeightedItem;

import java.util.List;

import static de.ccd.groupprio.repository.mapper.WeightMapperMongo.mapToBasicDBList;
import static de.ccd.groupprio.repository.mapper.WeightMapperMongo.mapToWeightedItemList;

public class WeightRepositoryMongo implements WeightRepository {
    private final DBCollection weightCollection;

    public WeightRepositoryMongo(DB db) {
        weightCollection = db.getCollection("weights");
        initUniqueKey();
    }

    private void initUniqueKey() {
        DBObject key = new BasicDBObject();
        key.put("project_id", 1);
        DBObject unique = new BasicDBObject();
        unique.put("unique", true);
        weightCollection.createIndex(key, unique);
    }

    @Override
    public List<WeightedItem> findForProjectId(String projectId) {
        BasicDBObject query = new BasicDBObject();
        query.put("project_id", projectId);
        DBObject weight = weightCollection.findOne(query);
        return mapToWeightedItemList(weight);
    }

    @Override
    public void save(String projectId, List<WeightedItem> items) {
        BasicDBList weightedList = mapToBasicDBList(items);
        insertOrUpdate(projectId, weightedList);
    }

    private void insertOrUpdate(String projectId, BasicDBList weightedList) {
        DBObject dbWeight = new BasicDBObject("project_id", projectId);
        DBObject found = weightCollection.findOne(dbWeight);
        if (found != null) {
            found.put("weighted_items", weightedList);
            weightCollection.save(found);
        } else {
            dbWeight.put("weighted_items", weightedList);
            weightCollection.save(dbWeight);
        }
    }
}
