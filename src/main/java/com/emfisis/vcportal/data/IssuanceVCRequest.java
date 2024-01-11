package com.emfisis.vcportal.data;

import java.io.Serializable;

public class IssuanceVCRequest implements Serializable {

    public IssuanceVCRequest() { }
    private IssuanceKey issuanceKey;
    private String issuerDid;
    private String username;

    public IssuanceVCRequest(String jwk, String issuerDid, String username) {
        this.issuanceKey = new IssuanceKey(jwk);
        this.issuerDid = issuerDid;
        this.username = username;
    }

    public IssuanceKey getIssuanceKey() {
        return issuanceKey;
    }

    public String getIssuerDid() {
        return issuerDid;
    }

    public void setIssuerDid(String issuerDid) {
        this.issuerDid = issuerDid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
