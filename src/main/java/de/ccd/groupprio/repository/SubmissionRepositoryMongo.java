package de.ccd.groupprio.repository;

import com.mongodb.*;
import de.ccd.groupprio.domain.data.Submission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.ccd.groupprio.repository.mapper.SubmissionMapperMongo.mapToSubmissionList;

public class SubmissionRepositoryMongo implements SubmissionRepository {
    public static final String PROJECT_ID = "project_id";
    public static final String CLIENT_ID = "client_id";
    public static final String PRIO_ITEMS = "prio_items";

    private final Map<String, List<Submission>> submissions = new HashMap<>();
    private final DBCollection submissionCollection;
    private final DBCollection submitterCollection;

    public SubmissionRepositoryMongo(DB db) {
        submissionCollection = db.getCollection("submissions");
        submitterCollection = db.getCollection("submitters");
    }

    @Override
    public List<Submission> findForProjectId(String projectId) {
        BasicDBObject query = new BasicDBObject();
        query.put(PROJECT_ID, projectId);
        DBCursor cursor = submissionCollection.find(query);
        return mapToSubmissionList(cursor);
    }

    @Override
    public void save(String projectId, String clientId, Submission submission) {
        saveSubmitter(projectId, clientId);
        saveSubmission(projectId, submission);
    }

    private void saveSubmitter(String projectId, String clientId) {
        BasicDBObject submitter = new BasicDBObject(PROJECT_ID, projectId)
                .append(CLIENT_ID, clientId);
        submitterCollection.insert(submitter);
    }

    private void saveSubmission(String projectId, Submission submission) {
        BasicDBList prioList = new BasicDBList();
        for (int i = 0; i < submission.getItems().size(); i++) {
            prioList.put(i, submission.getItemName(i));
        }
        DBObject dbSubmission = new BasicDBObject(PROJECT_ID, projectId).append(PRIO_ITEMS, prioList);
        submissionCollection.insert(dbSubmission);
    }

    @Override
    public int getSubmissionCount(String projectId) {
        return findForProjectId(projectId).size();
    }

    @Override
    public boolean hasClientSubmitted(String projectId, String clientId) {
        var qry = new BasicDBObject();
        qry.put(PROJECT_ID, projectId);
        qry.put(CLIENT_ID, clientId);
        return submitterCollection.count(qry) != 0;
    }
}
