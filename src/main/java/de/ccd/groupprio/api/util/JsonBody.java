package de.ccd.groupprio.api.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonBody {

    public static List<String> stringList(JsonNode node) {
        List<String> items = new ArrayList<>(node.size());
        node.elements().forEachRemaining(item -> items.add(item.asText()));
        return items;
    }

    public static Set<String> stringSet(JsonNode node) {
        Set<String> items = new HashSet<>(node.size());
        node.elements().forEachRemaining(item -> items.add(item.asText()));
        return items;
    }
}
