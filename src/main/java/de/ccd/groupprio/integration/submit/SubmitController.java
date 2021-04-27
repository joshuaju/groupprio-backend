package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.event_store.EventStore;

import static de.ccd.groupprio.integration.util.JsonUtil.*;

public class SubmitController {

    public SubmitController(EventStore eventStore) {
        var processor = new SubmitProcessor(eventStore);
        spark.Spark.post("project/:id/submission", (req, res) -> {
            var cmd = SubmitCommand.from(req);
            return processor.process(cmd);
        }, json());
    }
}
