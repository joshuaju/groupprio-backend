package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Project;

import java.util.List;

public interface ProjectRepository {

    Project get(String uuid);

    String save(Project project);

    List<Project> getByClientId(String clientId);
}
