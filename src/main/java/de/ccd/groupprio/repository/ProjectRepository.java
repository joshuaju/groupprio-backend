package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Project;

public interface ProjectRepository {

    Project get(String uuid);

    String save(Project project);
}
