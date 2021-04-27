package de.ccd.groupprio.integration.project.get.all;

import de.ccd.groupprio.event_store.EventStore;

import static de.ccd.groupprio.integration.util.JsonUtil.*;
import static spark.Spark.*;

public class GetAllProjectsController {

    public GetAllProjectsController(EventStore eventStore) {
        var processor = new GetAllProjectsProcessor(eventStore);

        get("/project", (req, res) -> {
            var qry = GetAllProjectsQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
