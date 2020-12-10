package de.ccd.groupprio.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import lombok.SneakyThrows;

import java.util.Deque;
import java.util.Optional;

public class Exchange {

    @SneakyThrows
    public static JsonNode jsonBody(HttpServerExchange exchange) {
        var om = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return om.readTree(exchange.getInputStream());
    }

    public static String pathParam(HttpServerExchange exchange, String param) {
        return Optional.ofNullable(exchange.getQueryParameters().get(param))
                .map(Deque::getFirst)
                .orElseThrow();
    }

    @SneakyThrows
    public static void sendJson(HttpServerExchange exchange, Object object) {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var jsonResponse = mapper.writeValueAsString(object);
        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(jsonResponse);
    }
}
