package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetAllProjectsProcessor {

    final ProjectRepository projectRepository;

    List<ProjectResponse> process(GetAllProjectsQuery qry) {
        return projectRepository
                .getByClientId(qry.clientId).stream()
                .map(ProjectResponse::map)
                .collect(Collectors.toList());
    }

}
