package de.ccd.groupprio.domain.project;

import java.util.HashMap;
import java.util.Map;

public class ProjectRepositoryMem implements ProjectRepository {

    private Map<Long, Project> projects = new HashMap<>();
    private IDGenerator idGenerator = new IDGenerator();

    @Override
    public Project get(long id) {
        return projects.getOrDefault(id, null);
    }

    @Override
    public long save(Project project) {
        long id = idGenerator.next();
        projects.put(id, project);
        return id;
    }

    private static class IDGenerator {
        static long nextId = 0L;

        private long next() {
            return nextId++;
        }
    }
}
