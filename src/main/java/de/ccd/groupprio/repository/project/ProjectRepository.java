package de.ccd.groupprio.repository.project;

import de.ccd.groupprio.domain.data.Project;

import java.util.List;

public interface ProjectRepository {

    Project getByProjectId(String uuid);

    List<Project> getByClientId(String clientId);

    String save(Project project);
}
