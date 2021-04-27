package de.ccd.groupprio.integration.project.state;

import de.ccd.groupprio.event_store.EventStore;

import static de.ccd.groupprio.integration.util.JsonUtil.*;
import static spark.Spark.*;

public class GetProjectStateController {

    public GetProjectStateController(EventStore eventStore) {
        var processor = new GetProjectStateProcessor(eventStore);
        get("/project/:id/prioritization", (req, res) -> {
            var qry = ProjectStateQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
