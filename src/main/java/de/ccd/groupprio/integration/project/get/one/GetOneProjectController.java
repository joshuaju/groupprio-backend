package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.event_store.EventStore;

import static de.ccd.groupprio.integration.util.JsonUtil.*;
import static spark.Spark.*;

public class GetOneProjectController {

    public GetOneProjectController(EventStore projectRepository) {
        var processor = new GetOneProjectProcessor(projectRepository);

        get("/project/:id", (req, res) -> {
            var qry = GetOneProjectQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
