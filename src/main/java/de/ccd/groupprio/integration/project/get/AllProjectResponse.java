package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.domain.data.Project;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class AllProjectsResponse
{
    final List<OverviewProject> projects;

    public static AllProjectsResponse from(List<Project> projects) {
        List<OverviewProject> mapped = projects.stream()
                                                .map(p -> new OverviewProject(p.getId(), p.getTitle()))
                                                .collect(Collectors.toList());
        return new AllProjectsResponse(mapped);
    }

    @RequiredArgsConstructor
    static class OverviewProject {
        final String id;
        final String title;
    }
}
