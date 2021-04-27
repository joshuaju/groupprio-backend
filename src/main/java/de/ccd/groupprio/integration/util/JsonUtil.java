package de.ccd.groupprio.integration.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import spark.ResponseTransformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtil {

    @SneakyThrows
    public static JsonNode jsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper()
                                        .registerModule(new JavaTimeModule())
                                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper.readTree(jsonString);
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static List<String> stringList(JsonNode node, String fieldName) {
        List<String> items = new ArrayList<>(node.size());
        node.get(fieldName).elements().forEachRemaining(item -> items.add(item.asText()));
        return items;
    }

    public static Set<String> stringSet(JsonNode node, String fieldName) {
        Set<String> items = new HashSet<>(node.size());
        node.get(fieldName).elements().forEachRemaining(item -> items.add(item.asText()));
        return items;
    }

    public static boolean asBoolean(JsonNode node, String fieldName, boolean defaultValue) {
        return node.get(fieldName) != null ? node.get(fieldName).asBoolean() : defaultValue;
    }
}
