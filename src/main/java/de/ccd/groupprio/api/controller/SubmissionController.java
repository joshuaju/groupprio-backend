package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.dto.PrioDto;
import de.ccd.groupprio.domain.submission.SubmissionService;

public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
        submit();
    }

    private void submit() {
        spark.Spark.post("project/:id/submission", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            PrioDto prioDto = JsonUtil.fromJson(req.body(), PrioDto.class);
            this.submissionService.submitWithRecalc(id, prioDto.items);
            return res;
        });
    }

}
