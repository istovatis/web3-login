package com.emfisis.vcportal.utils.RestCalling;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class ResponseDecoder {

    private static Gson gson = new JsonSupplier().get();

    public static <T> List<T> decodeList(ServerResponse serverResponse, Class<T> c) {
        if (serverResponse.getStatus() == 200) {
            Type type = TypeToken.getParameterized(List.class, c).getType();

            JsonArray responseContent = gson.fromJson(serverResponse.getEntity(), JsonArray.class);
            return gson.fromJson(responseContent, type);
        } else {
            return new ArrayList<>();
        }
    }

    public static <T, R> Map<T, R> decodeMap(ServerResponse serverResponse, Class<T> c, Class<R> d) {
        if (serverResponse.getStatus() == 200) {
            Type type = TypeToken.getParameterized(Map.class, new Type[]{c, d}).getType();

            JsonObject responseContent = gson.fromJson(serverResponse.getEntity(), JsonObject.class);
            return gson.fromJson(responseContent, type);
        } else {
            return new HashMap<>();
        }
    }

    public static <T> List<T> decodeList(ServerResponse serverResponse, String member) {
        JsonObject responseContent = gson.fromJson(serverResponse.getEntity(), JsonObject.class);
        JsonArray array = responseContent.get(member).getAsJsonArray();
        return gson.fromJson(array, new TypeToken<List<T>>() {
        }.getType());
    }
}
