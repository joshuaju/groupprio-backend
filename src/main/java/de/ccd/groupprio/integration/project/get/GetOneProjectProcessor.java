package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.domain.logic.IsSubmissionAllowed;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetOneProjectProcessor
{

    final ProjectRepository projectRepository;
    final SubmissionRepository submissionRepository;

    OneProjectResponse process(GetOneProjectQuery qry)
    {
        var project = projectRepository
                .getByProjectId(qry.projectId);


        var alreadySubmitted = submissionRepository.hasClientSubmitted(qry.projectId, qry.clientId);
        var isSubmissionAllowed = IsSubmissionAllowed.check(project, alreadySubmitted);

        return OneProjectResponse.from(project, isSubmissionAllowed);
    }

}
