package de.ccd.groupprio.domain.project;

public interface ProjectRepository {

    Project get(long id);

    long save(Project project);
}
