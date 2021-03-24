package de.ccd.groupprio.repository.project;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.ccd.groupprio.domain.data.Project;

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

    List<Project> mapToProjects(DBCursor projectCursor) {
        return projectCursor.toArray().stream()
                            .map(this::mapToProject)
                            .collect(Collectors.toList());
    }

    Project mapToProject(DBObject dbProject) {
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
