package de.ccd.groupprio.integration.project.state;

import de.ccd.groupprio.domain.data.WeightedItem;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetProjectStateProcessor {

    private final ProjectRepository projectRepository;
    private final WeightRepository weightRepository;
    private final SubmissionRepository submissionRepository;

    ProjectStateResponse process(ProjectStateQuery qry) {
        var project = projectRepository
                .getByProjectId(qry.projectId);

        var weightedItems = weightRepository
                .findForProjectId(qry.projectId).stream()
                .map(WeightedItem::getName)
                .collect(Collectors.toList());

        var submissionCount = submissionRepository
                .getSubmissionCount(qry.projectId);

        return new ProjectStateResponse(project.getTitle(), weightedItems, submissionCount);
    }

}
