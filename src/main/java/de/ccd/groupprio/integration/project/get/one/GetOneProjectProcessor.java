package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetOneProjectProcessor {

    final ProjectRepository projectRepository;

    OneProjectResponse process(GetOneProjectQuery qry) {
        var project = projectRepository
                                .getByProjectId(qry.projectId);

        var isSubmissionAllowed = project.isClientAllowedToSubmit(qry.clientId);

        return OneProjectResponse.from(project, isSubmissionAllowed);
    }
}
