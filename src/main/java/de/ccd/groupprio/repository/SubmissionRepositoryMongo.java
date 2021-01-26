package de.ccd.groupprio.repository;

import static de.ccd.groupprio.repository.mapper.SubmissionMapperMongo.mapToSubmissionList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.ccd.groupprio.domain.data.Submission;

public class SubmissionRepositoryMongo implements SubmissionRepository {
    private final Map<String, List<Submission>> submissions = new HashMap<>();
    private final DBCollection submissionCollection;

    public SubmissionRepositoryMongo(DB db) {
        submissionCollection = db.getCollection("submissions");
    }

    @Override
    public List<Submission> findForProjectId(String projectId) {
        BasicDBObject query = new BasicDBObject();
        query.put("project_id", projectId);
        DBCursor cursor = submissionCollection.find(query);
        return mapToSubmissionList(cursor);
    }

    @Override
    public void save(String projectId, Submission submission) {
        BasicDBList prioList = new BasicDBList();
        for (int i = 0; i < submission.getItems().size(); i++) {
            prioList.put(i, submission.getItemName(i));
        }
        DBObject dbSubmission = new BasicDBObject("project_id", projectId).append("prio_items", prioList);
        submissionCollection.insert(dbSubmission);
    }
}
