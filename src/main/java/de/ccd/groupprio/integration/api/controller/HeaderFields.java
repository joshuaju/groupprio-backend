package de.ccd.groupprio.integration.api.controller;

import spark.Request;

public class HeaderFields {

    public static final String CLIENT_ID = "clientID";

    public static String getOrFail(Request request, String header) {
        var value = request.headers(header);
        if (value == null) {
            throw new IllegalArgumentException("Missing header field: " + header);
        }
        return value;
    }
}
