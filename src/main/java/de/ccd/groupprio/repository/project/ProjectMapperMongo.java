package de.ccd.groupprio.repository.project;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import de.ccd.groupprio.domain.data.Project;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapperMongo {

    private ProjectMapperMongo() {
    }

    public static Project mapToProject(DBObject dbProject) {
        String id = (String) dbProject.get("id");
        String title = (String) dbProject.get("title");
        String clientId = (String) dbProject.get("clientId");
        BasicDBList itemList = (BasicDBList) dbProject.get("items");
        boolean isMultiSubmissionAllowed = (boolean) dbProject.get("isMultiSubmissionsAllowed");
        Set<String> items = itemList.stream().map(Object::toString).collect(Collectors.toSet());
        return new Project(id, title, items, isMultiSubmissionAllowed, clientId);
    }
}
