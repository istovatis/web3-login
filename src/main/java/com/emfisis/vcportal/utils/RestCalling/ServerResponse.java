package com.emfisis.vcportal.utils.RestCalling;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerResponse {

    private int status;
    private String entity;
    private String error;

    private String errorMessage;

    private Map<String, String> headers = new HashMap<>();

    public ServerResponse() {
    }

    public ServerResponse(int status, String entity,Map<String, String> headers) {
        this.status = status;
        this.entity = entity;
        this.headers = headers;
    }

    public ServerResponse(int status, String entity, String error, String errorMessage, Map<String, String> headers) {
        this.status = status;
        this.entity = entity;
        this.error = error;
        this.errorMessage = errorMessage;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
