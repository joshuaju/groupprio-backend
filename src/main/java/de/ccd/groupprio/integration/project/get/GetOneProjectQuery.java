package de.ccd.groupprio.integration.project.get;

import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
class GetOneProjectQuery {
    final String projectId;

    public static GetOneProjectQuery from(Request req) {
        return new GetOneProjectQuery(req.params(":id"));
    }
}
