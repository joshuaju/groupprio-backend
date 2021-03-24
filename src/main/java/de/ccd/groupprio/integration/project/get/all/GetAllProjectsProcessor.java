package de.ccd.groupprio.integration.project.get.all;

import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetAllProjectsProcessor
{

    final ProjectRepository projectRepository;

    AllProjectsResponse process(GetAllProjectsQuery qry)
    {
        var projects = projectRepository.getByClientId(qry.clientId);
        return AllProjectsResponse.from(projects);
    }

}
