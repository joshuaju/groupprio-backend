package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.event_store.EventStore;

import static de.ccd.groupprio.integration.util.JsonUtil.*;
import static spark.Spark.*;

public class CreateProjectController {

    public CreateProjectController(EventStore eventStore) {
        var processor = new CreateProjectProcessor(eventStore);

        post("/project", (req, res) -> {
            var cmd = CreateProjectCommand.from(req);
            return processor.process(cmd);
        }, json());
    }
}
