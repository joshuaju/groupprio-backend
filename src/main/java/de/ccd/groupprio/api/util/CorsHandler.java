package de.ccd.groupprio.api.util;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.HttpString;

public class CorsHandler implements HttpHandler {

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private HttpHandler next;

    public CorsHandler(HttpHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (!hasHeader(exchange, ACCESS_CONTROL_ALLOW_ORIGIN)) {
            var origin = origin(exchange);
            addHeader(exchange, ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }

        if (!hasHeader(exchange, ACCESS_CONTROL_ALLOW_METHODS)) {
            addHeader(exchange, ACCESS_CONTROL_ALLOW_METHODS, "DELETE,GET,HEAD,OPTIONS,PATCH,POST,PUT");
        }

        if (!hasHeader(exchange, ACCESS_CONTROL_ALLOW_HEADERS)) {
            addHeader(exchange, ACCESS_CONTROL_ALLOW_HEADERS, "Authorization,Content-Type,Link,X-Total-Count,Range");
        }

        next.handleRequest(exchange);
    }

    private static String origin(HttpServerExchange exchange) {
        HeaderValues headers = exchange.getRequestHeaders().get("Origin");
        return headers == null ? null : headers.peekFirst();
    }

    private static boolean hasHeader(HttpServerExchange exchange, String name) {
        return exchange.getResponseHeaders().get(name) != null;
    }

    private static void addHeader(HttpServerExchange exchange, String name, String value) {
        exchange.getResponseHeaders().add(HttpString.tryFromString(name), value);
    }
}
