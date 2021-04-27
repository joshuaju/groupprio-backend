package de.ccd.groupprio.integration.project.get.one;

import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
class GetOneProjectQuery {

    final String projectId;
    final String clientId;

    public static GetOneProjectQuery from(Request req) {
        return new GetOneProjectQuery(req.params(":id"), req.headers("clientId"));
    }
}
