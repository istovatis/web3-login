package com.emfisis.vcportal.data;

import java.io.Serial;
import java.io.Serializable;

public class IssuanceKey implements Serializable {

    private String type = "local";
    private String jwk;

    public IssuanceKey(String jwk) {
        this.jwk = jwk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJwk() {
        return jwk;
    }

    public void setJwk(String jwk) {
        this.jwk = jwk;
    }
}
