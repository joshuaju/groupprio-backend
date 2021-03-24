package de.ccd.groupprio.repository.project;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.repository.submission.SubmissionRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryMongo implements ProjectRepository {

    private final DBCollection projectCollection;
    private final ProjectMapperMongo projectMapper;

    public ProjectRepositoryMongo(SubmissionRepository submissionRepository, DB db) {
        projectCollection = db.getCollection("projects");
        this.projectMapper = new ProjectMapperMongo(submissionRepository::getSubmitters);
    }

    @Override
    public Project getByProjectId(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", id);
        DBObject dbObj = projectCollection.findOne(query);
        return projectMapper.mapToProject(dbObj);
    }

    @Override
    public String save(Project project) {
        DBObject dbProject = new BasicDBObject("title", project.getTitle())
                                       .append("id", project.getId())
                                       .append("items", project.getItems())
                                       .append("isMultiSubmissionsAllowed", project.isMultiSubmissionAllowed())
                                       .append("clientId", project.getClientId());
        projectCollection.insert(dbProject);
        return project.getId();
    }

    @Override
    public List<Project> getByClientId(final String clientId) {
        BasicDBObject query = new BasicDBObject();
        query.put("clientId", clientId);
        var projectCursor = projectCollection.find(query);
        return projectMapper.mapToProjects(projectCursor);
    }
}
