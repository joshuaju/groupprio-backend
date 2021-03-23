package de.ccd.groupprio.repository.project;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import de.ccd.groupprio.domain.data.Project;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.ccd.groupprio.repository.project.ProjectMapperMongo.*;

public class ProjectRepositoryMongo implements ProjectRepository {

    private final MongoCollection<Document> projectCollection;

    public ProjectRepositoryMongo(MongoDatabase db) {
        projectCollection = db.getCollection("projects");
        initUniqueTitleAndClientIdIndex();
    }

    private void initUniqueTitleAndClientIdIndex() {
        Document indexFields = new Document("title", 1).append("clientId", 1);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        projectCollection.createIndex(indexFields, indexOptions);
    }

    @Override
    public Project getByProjectId(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projectDocs = projectCollection.find(query).first();
        return mapToProject(Objects.requireNonNull(projectDocs));
    }

    @Override
    public String save(Project project) {
        Document projectDoc = new Document("title", project.getTitle()).append("id", project.getId())
                                                                       .append("items", project.getItems())
                                                                       .append("isMultiSubmissionsAllowed", project.isMultiSubmissionAllowed())
                                                                       .append("clientId", project.getClientId());
        projectCollection.insertOne(projectDoc);
        return project.getId();
    }

    @Override
    public List<Project> getByClientId(final String clientId) {
        List<Project> projects = new ArrayList<>();
        Document query = new Document();
        query.put("clientId", clientId);
        var projectCursor = projectCollection.find(query);
        for (Document projectDoc : projectCursor) {
            projects.add(mapToProject(projectDoc));
        }
        return projects;
    }
}
