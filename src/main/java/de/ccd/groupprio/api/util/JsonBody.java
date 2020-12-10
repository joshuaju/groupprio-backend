package de.ccd.groupprio.api.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonBody {

    public static List<String> stringList(JsonNode node) {
        List<String> items = new ArrayList<>(node.size());
        node.elements().forEachRemaining(item -> items.add(item.asText()));
        return items;
    }
}
