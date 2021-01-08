package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.dto.PrioDto;
import de.ccd.groupprio.domain.submission.SubmissionService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @POST
    @Path("/{id}")
    public Response submit(@PathParam("id") long id, PrioDto prio) {
        submissionService.submitWithRecalc(id, prio.items);
        return Response.ok().build();
    }
}
