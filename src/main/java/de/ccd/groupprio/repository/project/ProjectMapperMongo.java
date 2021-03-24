package de.ccd.groupprio.repository.project;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoIterable;
import de.ccd.groupprio.domain.data.Project;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        String id = (String) dbProject.get("id");
        String title = (String) dbProject.get("title");
        String clientId = (String) dbProject.get("clientId");
        BasicDBList itemList = (BasicDBList) dbProject.get("items");
        boolean isMultiSubmissionAllowed = (boolean) dbProject.get("isMultiSubmissionsAllowed");
        Set<String> items = itemList.stream().map(Object::toString).collect(Collectors.toSet());

        var submitterIds = this.getSubmittersForProjectID.apply(id);

        return new Project(id, title, items, new HashSet<>(submitterIds), isMultiSubmissionAllowed, clientId);
    }
}
