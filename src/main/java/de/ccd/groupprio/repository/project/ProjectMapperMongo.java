package de.ccd.groupprio.repository.project;

import com.mongodb.client.MongoIterable;
import de.ccd.groupprio.domain.data.Project;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class ProjectMapperMongo {

    private final Function<String, List<String>> getSubmittersForProjectID;

    ProjectMapperMongo(Function<String, List<String>> getSubmittersForProjectID) {
        this.getSubmittersForProjectID = getSubmittersForProjectID;
    }

    List<Project> mapToProjects(MongoIterable<Document> projectDocs) {
        List<Project> projects = new ArrayList<>();
        for (Document projectDoc : projectDocs) {
            var project = mapToProject(projectDoc);
            projects.add(project);
        }
        return projects;
    }

    Project mapToProject(Document dbProject) {
        String id = dbProject.getString("id");
        String title = dbProject.getString("title");
        String clientId = dbProject.getString("clientId");
        var itemList = dbProject.getList("items", String.class);
        boolean isMultiSubmissionAllowed = dbProject.getBoolean("isMultiSubmissionsAllowed");
        var items = new HashSet<>(itemList);
        var submitterIds = this.getSubmittersForProjectID.apply(id);

        return new Project(id, title, items, new HashSet<>(submitterIds), isMultiSubmissionAllowed, clientId);
    }
}
