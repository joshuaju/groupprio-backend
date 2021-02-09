package de.ccd.groupprio.repository;

import static de.ccd.groupprio.repository.mapper.ProjectMapperMongo.mapToProject;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import de.ccd.groupprio.domain.data.Project;

public class ProjectRepositoryMongo implements ProjectRepository {

    private final DBCollection projectCollection;

    public ProjectRepositoryMongo(DB db) {
        projectCollection = db.getCollection("projects");
    }

    @Override
    public Project get(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = projectCollection.findOne(query);
        return mapToProject(dbObj);
    }

    @Override
    public String save(Project project) {
        DBObject dbProject = new BasicDBObject("title", project.getTitle()).append("items", project.getItems()).append("isMultiSubmissionsAllowed", project.isMultipleSubmissionsAllowed());
        projectCollection.insert(dbProject);
        ObjectId id = (ObjectId) dbProject.get("_id");
        return id.toString();
    }
}
