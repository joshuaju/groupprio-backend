package de.ccd.groupprio.api.controller;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
