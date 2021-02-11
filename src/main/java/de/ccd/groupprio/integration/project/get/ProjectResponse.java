package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.domain.data.Project;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
class ProjectResponse {
    final String id;
    final String title;
    final Set<String> items;
    final boolean isMultipleSubmissionsAllowed;

    public static ProjectResponse map(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getItems(),
                project.isMultipleSubmissionsAllowed()
        );
    }
}
