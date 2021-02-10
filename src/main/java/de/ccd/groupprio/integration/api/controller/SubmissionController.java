package de.ccd.groupprio.integration.api.controller;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.integration.api.dto.PrioDto;
import de.ccd.groupprio.integration.services.SubmissionService;

import java.util.Map;
import java.util.stream.Collectors;

import static de.ccd.groupprio.integration.api.controller.JsonUtil.json;

public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
        submit();
    }

    private void submit() {
        spark.Spark.post("project/:id/submission", (req, res) -> {
            var clientId = req.headers("clientId");
            var projectId = req.params(":id");
            var prioDto = JsonUtil.fromJson(req.body(), PrioDto.class);

            var success = this.submissionService.submitWithRecalc(projectId, clientId,
                    prioDto.items.stream().map(PrioItem::new).collect(Collectors.toList()));

            return Map.of("success", success);
        }, json());
    }
}
