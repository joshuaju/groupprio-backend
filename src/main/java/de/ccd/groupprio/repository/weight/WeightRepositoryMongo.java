package de.ccd.groupprio.repository.weight;

import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import de.ccd.groupprio.domain.data.WeightedItem;
import org.bson.Document;

import java.util.List;

import static de.ccd.groupprio.repository.weight.WeightMapperMongo.*;

public class WeightRepositoryMongo implements WeightRepository {

    private final MongoCollection<Document> weightCollection;

    public WeightRepositoryMongo(MongoDatabase db) {
        weightCollection = db.getCollection("weights");
        initUniqueProjectIdIndex();
    }

    private void initUniqueProjectIdIndex() {
        var key = new Document("project_id", 1);
        var indexOptions = new IndexOptions();
        indexOptions.unique(true);
        weightCollection.createIndex(key, indexOptions);
    }

    @Override
    public List<WeightedItem> findForProjectId(String projectId) {
        Document query = new Document();
        query.put("project_id", projectId);
        Document weight = weightCollection.find(query).first();
        return mapToWeightedItemList(weight);
    }

    @Override
    public void save(String projectId, List<WeightedItem> items) {
        BasicDBList weightedList = mapToBasicDBList(items);
        insertOrUpdate(projectId, weightedList);
    }

    private void insertOrUpdate(String projectId, BasicDBList weightedList) {
        Document weightDoc = new Document("project_id", projectId);
        FindIterable<Document> found = weightCollection.find(weightDoc);
        if (found.cursor().hasNext()) {
            Document projectToUpdate = found.first();
            projectToUpdate.put("weighted_items", weightedList);
            weightCollection.findOneAndReplace(weightDoc, projectToUpdate);
        } else {
            weightDoc.put("weighted_items", weightedList);
            weightCollection.insertOne(weightDoc);
        }
    }
}
