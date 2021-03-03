package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.integration.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import spark.Request;

import java.util.List;

import static de.ccd.groupprio.integration.util.JsonUtil.stringList;

@RequiredArgsConstructor
class SubmitCommand {

    final String clientId;
    final String projectId;
    final List<String> items;

    static SubmitCommand from(Request req) {
        var body = JsonUtil.jsonNode(req.body());
        return new SubmitCommand(
                req.headers("clientId"),
                req.params(":id"),
                stringList(body, "items")
        );
    }
}
