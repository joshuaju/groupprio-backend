package de.ccd.groupprio.integration.project.get;

import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
class GetAllProjectsQuery {
    final String clientId;

    static GetAllProjectsQuery from(Request req){
        return new GetAllProjectsQuery(req.headers("clientId"));
    }
}
