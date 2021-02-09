package de.ccd.groupprio.repository;

import de.ccd.groupprio.domain.data.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectRepositoryMem implements ProjectRepository {

    private final Map<String, Project> projects = new HashMap<>();
    private final IDGenerator idGenerator = new IDGenerator();

    @Override
    public Project get(String id) {
        return projects.getOrDefault(id, null);
    }

    @Override
    public String save(Project project) {
        String id = idGenerator.next();
        projects.put(id, project);
        return id;
    }

    private static class IDGenerator {
        static long nextId = 0L;

        private String next() {
            return String.format("%d", nextId++);
        }
    }
}
