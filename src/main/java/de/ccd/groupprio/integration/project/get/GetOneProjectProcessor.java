package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetOneProjectProcessor {

    final ProjectRepository projectRepository;

    ProjectResponse process(GetOneProjectQuery qry) {
        var project = projectRepository
                .getByProjectId(qry.projectId);
        return ProjectResponse.map(project);
    }

}
