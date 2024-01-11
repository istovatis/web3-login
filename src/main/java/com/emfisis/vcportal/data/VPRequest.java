package com.emfisis.vcportal.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VPRequest implements Serializable {

    public final static String emfisisCredential = "Emfisis";

    public final static String expired = "expired";

    public VPRequest() {}

    private List<String> request_credentials = new ArrayList<>();

    private List<String> vc_policies;

    public void addRequestCredential(String credential) {
        request_credentials.add(credential);
    }

    public void addVCPolicy(String policy) {
        if (vc_policies == null) {
            vc_policies = new ArrayList<>();
        }
        vc_policies.add(policy);
    }

    public List<String> getRequest_credentials() {
        return request_credentials;
    }

    public List<String> getVc_policies() {
        return vc_policies;
    }
}
