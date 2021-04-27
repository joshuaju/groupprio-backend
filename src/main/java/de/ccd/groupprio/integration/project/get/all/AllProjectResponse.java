package de.ccd.groupprio.integration.project.get.all;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class AllProjectsResponse {

    final List<OverviewProject> projects;

    @RequiredArgsConstructor
    static class OverviewProject {

        final String id;
        final String title;
    }
}
