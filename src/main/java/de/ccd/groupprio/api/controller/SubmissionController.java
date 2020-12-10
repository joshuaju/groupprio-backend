package de.ccd.groupprio.api.controller;

import de.ccd.groupprio.api.util.Exchange;
import de.ccd.groupprio.api.util.JsonBody;
import de.ccd.groupprio.domain.submission.PrioItem;
import de.ccd.groupprio.domain.submission.SubmissionService;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    public void submit(HttpServerExchange exchange) {
        var id = Long.parseLong(Exchange.pathParam(exchange, "id"));

        var body = Exchange.jsonBody(exchange);
        var items = JsonBody.stringList(body.get("items")).stream()
                .map(PrioItem::new)
                .collect(Collectors.toList());

        submissionService.submitWithRecalc(id, items);

        exchange.setStatusCode(StatusCodes.OK);
    }
}
