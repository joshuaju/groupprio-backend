package de.ccd.groupprio.integration.project.state;

import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
class ProjectStateQuery {

    final String projectId;

    static ProjectStateQuery from(Request req) {
        return new ProjectStateQuery(req.params(":id"));
    }
}
