package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Project;

public interface ProjectRepository {

    Project get(long id);

    long save(Project project);
}
