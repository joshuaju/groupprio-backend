package de.ccd.groupprio.repository.project;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.ccd.groupprio.domain.data.Project;

import static de.ccd.groupprio.repository.project.ProjectMapperMongo.mapToProject;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryMongo implements ProjectRepository {

    private final DBCollection projectCollection;

    public ProjectRepositoryMongo(DB db) {
        projectCollection = db.getCollection("projects");
    }

    @Override
    public Project getByProjectId(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", id);
        DBObject dbObj = projectCollection.findOne(query);
        return mapToProject(dbObj);
    }

    @Override
    public String save(Project project) {
        DBObject dbProject = new BasicDBObject("title", project.getTitle())
                                       .append("id", project.getId())
                                       .append("items", project.getItems())
                                       .append("isMultiSubmissionsAllowed", project.isMultipleSubmissionsAllowed())
                                       .append("clientId", project.getClientId());
        projectCollection.insert(dbProject);
        return project.getId();
    }

    @Override
    public List<Project> getByClientId(final String clientId) {
        List<Project> projects = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        query.put("clientId", clientId);
        final var projectCursor = projectCollection.find(query);
        for (final DBObject dbObject : projectCursor) {
            projects.add(mapToProject(dbObject));
        }
        return projects;
    }
}
