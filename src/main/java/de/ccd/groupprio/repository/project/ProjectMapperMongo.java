package de.ccd.groupprio.repository.project;

import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.BasicDBList;

import de.ccd.groupprio.domain.data.Project;

public class ProjectMapperMongo {

    private ProjectMapperMongo() {
    }

    public static Project mapToProject(Document dbProject) {
        String id = (String) dbProject.get("id");
        String title = (String) dbProject.get("title");
        String clientId = (String) dbProject.get("clientId");
        BasicDBList itemList = (BasicDBList) dbProject.get("items");
        boolean isMultiSubmissionAllowed = (boolean) dbProject.get("isMultiSubmissionsAllowed");
        Set<String> items = itemList.stream().map(Object::toString).collect(Collectors.toSet());
        return new Project(id, title, items, isMultiSubmissionAllowed, clientId);
    }
}
