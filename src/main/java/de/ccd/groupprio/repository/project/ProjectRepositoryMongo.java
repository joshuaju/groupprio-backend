package de.ccd.groupprio.repository.project;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import org.bson.Document;

import java.util.List;
import java.util.Objects;

public class ProjectRepositoryMongo implements ProjectRepository {

    private final MongoCollection<Document> projectCollection;
    private final ProjectMapperMongo projectMapper;

    public ProjectRepositoryMongo(SubmissionRepository submissionRepository, MongoDatabase db) {
        projectCollection = db.getCollection("projects");
        this.projectMapper = new ProjectMapperMongo(submissionRepository::getSubmitters);
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
        return projectMapper.mapToProject(Objects.requireNonNull(projectDocs));
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
        Document query = new Document();
        query.put("clientId", clientId);
        var projectDocs = projectCollection.find(query);
        return projectMapper.mapToProjects(projectDocs);
    }
}
