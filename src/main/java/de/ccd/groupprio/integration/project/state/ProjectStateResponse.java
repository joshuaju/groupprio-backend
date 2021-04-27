package de.ccd.groupprio.integration.project.state;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class ProjectStateResponse {

    final String title;
    final List<String> weightedItems;
    final long submissionCount;
}
