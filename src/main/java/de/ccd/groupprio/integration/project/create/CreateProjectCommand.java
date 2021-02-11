package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.integration.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import spark.Request;

import java.util.Set;

import static de.ccd.groupprio.integration.util.JsonUtil.asBoolean;
import static de.ccd.groupprio.integration.util.JsonUtil.stringSet;

@RequiredArgsConstructor
class CreateProjectCommand {
    final String title;
    final Set<String> items;
    final String createdByClientId;
    final boolean multiSubmission;

    static CreateProjectCommand from(Request req) {
        var body = JsonUtil.jsonNode(req.body());
        return new CreateProjectCommand(
                body.get("title").asText(),
                stringSet(body, "items"),
                req.headers("clientId"),
                asBoolean(body, "isMultipleSubmissionsAllowed", false)
        );
    }
}
