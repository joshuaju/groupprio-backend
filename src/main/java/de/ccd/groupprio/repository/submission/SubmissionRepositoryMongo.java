package de.ccd.groupprio.repository.submission;

import static de.ccd.groupprio.repository.submission.SubmissionMapperMongo.*;

import java.util.List;

import com.mongodb.BasicDBObject;
import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.ccd.groupprio.domain.data.Submission;

public class SubmissionRepositoryMongo implements SubmissionRepository {
    public static final String PROJECT_ID = "project_id";
    public static final String CLIENT_ID = "client_id";
    public static final String PRIO_ITEMS = "prio_items";

    private final MongoCollection<Document> submissionCollection;
    private final MongoCollection<Document> submitterCollection;

    public SubmissionRepositoryMongo(MongoDatabase db) {
        submissionCollection = db.getCollection("submissions");
        submitterCollection = db.getCollection("submitters");
    }

    @Override
    public List<Submission> findForProjectId(String projectId) {
        Document query = new Document();
        query.put(PROJECT_ID, projectId);
        FindIterable<Document> cursor = submissionCollection.find(query);
        return mapToSubmissionList(cursor);
    }

    @Override
    public void save(String projectId, String clientId, Submission submission) {
        saveSubmitter(projectId, clientId);
        saveSubmission(projectId, submission);
    }

    private void saveSubmitter(String projectId, String clientId) {
        Document submitter = new Document(PROJECT_ID, projectId).append(CLIENT_ID, clientId);
        submitterCollection.insertOne(submitter);
    }

    private void saveSubmission(String projectId, Submission submission) {
        BasicDBList prioList = new BasicDBList();
        for (int i = 0; i < submission.getItems().size(); i++) {
            prioList.put(i, submission.getItemName(i));
        }
        Document dbSubmission = new Document(PROJECT_ID, projectId).append(PRIO_ITEMS, prioList);
        submissionCollection.insertOne(dbSubmission);
    }

    @Override
    public int getSubmissionCount(String projectId) {
        return findForProjectId(projectId).size();
    }

    @Override
    public boolean hasClientSubmitted(String projectId, String clientId) {
        var qry = new Document();
        qry.put(PROJECT_ID, projectId);
        qry.put(CLIENT_ID, clientId);
        return submitterCollection.countDocuments(qry) != 0;
    }

    @Override
    public List<String> getSubmitters(String projectId) {
        var qry = new BasicDBObject();
        qry.put(PROJECT_ID, projectId);
        var submitters = submitterCollection.find(qry);
        return mapToSubmitterIds(submitters);
    }
}
