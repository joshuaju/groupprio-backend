package de.ccd.groupprio.repository.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import de.ccd.groupprio.domain.data.Project;

public class ProjectMapperMongo {

    private ProjectMapperMongo() {
    }

    public static Project mapToProject(DBObject dbProject) {
        String title = (String) dbProject.get("title");
        BasicDBList itemList = (BasicDBList) dbProject.get("items");
        Set<String> items = itemList.stream().map(Object::toString).collect(Collectors.toSet());
        return new Project(title, items);
    }
}