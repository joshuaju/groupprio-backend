package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.domain.data.ProjectAggregate;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
class OneProjectResponse {

    final String id;
    final String title;
    final Set<String> items;
    final boolean isSubmissionAllowed;

    public static OneProjectResponse from(ProjectAggregate project, boolean isSubmissionAllowed) {
        return new OneProjectResponse(
                  project.getId(),
                  project.getTitle(),
                  project.getItems(),
                  isSubmissionAllowed
        );
    }
}
