package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.domain.data.Project;
import de.ccd.groupprio.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateProjectProcessor {

    private final ProjectRepository projectRepository;

    public CreatedProjectResponse process(CreateProjectCommand cmd) {
        var project = new Project(cmd.title, cmd.items, cmd.multiSubmission, cmd.createdByClientId);
        var id = projectRepository.save(project);
        return new CreatedProjectResponse(id);
    }

}
