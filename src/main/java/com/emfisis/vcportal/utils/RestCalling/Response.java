package com.emfisis.vcportal.utils.RestCalling;

import com.google.gson.JsonObject;

public class Response<T> {

    public ServerResponse serverResponse;
    public T entity;

    public Response(ServerResponse serverResponse, Class en) {
        this.serverResponse = serverResponse;
        if (Integer.valueOf(200).equals((serverResponse.getStatus()))) {
            entity = (T) new JsonSupplier().get().fromJson(serverResponse.getEntity(), en);
        }
    }

    public Response(ServerResponse serverResponse, String field, Class c) {
        this.serverResponse = serverResponse;
        if (Integer.valueOf(200).equals((serverResponse.getStatus()))) {
            JsonObject responseContent = new JsonSupplier().get().fromJson(serverResponse.getEntity(), JsonObject.class);
            if (c.isAssignableFrom(Long.class)) {
                entity = (T) Long.valueOf(responseContent.get(field).getAsLong());
            } else if (c.isAssignableFrom(String.class)) {
                entity = (T) Long.valueOf(responseContent.get(field).getAsString());
            }
        }
    }

    public Response(ServerResponse serverResponse, T entity) {
        this.serverResponse = serverResponse;
        this.entity = entity;
    }
}
