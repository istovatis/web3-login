package com.emfisis.vcportal;

import com.emfisis.vcportal.data.IssuanceVCRequest;
import com.emfisis.vcportal.data.VPRequest;
import com.emfisis.vcportal.utils.RestCalling.Response;
import com.emfisis.vcportal.utils.RestCalling.RestApiClient;
import com.emfisis.vcportal.utils.RestCalling.ServerResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Component
public class BackendCommunication {

    Logger logger = LoggerFactory.getLogger(BackendCommunication.class);

    @Value("${issuer-uri}")
    String issuerUri;

    @Value("${issuer-key}")
    String issuerKey;

    @Value("${issuer-did}")
    String issuerDID;

    @Value("${verifier-uri}")
    String verifierUri;

    private static Gson gson = new Gson();

    //verification callbacks just to see that we get calls
    //http://localhost:8080/verification-callback-api/success
    //http://localhost:8080/verification-callback-api/fail
    //where localhost the host of emfisis web3-login  ui portal(current vaadin service)
    public Response<String> createVerificationUrl() {
        logger.info("verifing vc");
        Response<String> verify = verify();
        logger.info(verify.serverResponse.getStatus() + ": " + verify.entity);
        return verify;
    }

    public Response<String> createIssuingLink(String username) {
        IssuanceVCRequest vc = new IssuanceVCRequest(issuerKey, issuerDID, username);
        logger.info("Issuing VC: KEY:" + vc.getIssuanceKey().getJwk() + "  DID:" + vc.getIssuerDid() + " Username:" + vc.getUsername());

        Response<String> issueResponse = issueVC(vc);
        logger.info("Issuing VC Response: " + issueResponse.serverResponse.getStatus() + " response: " + issueResponse.entity + " error: " + issueResponse.serverResponse.getError());
        return issueResponse;
    }

    public Response<String> issueVC(IssuanceVCRequest vc) {
        String json = gson.toJson(vc);
        ServerResponse serverResponse = RestApiClient.sendPOST(issuerUri + "/openid4vc/jwt/issue-emfisis", json, Optional.empty());
        return new Response<>(serverResponse, serverResponse.getEntity());
    }

    public Response<String> verify() {
        VPRequest credentials = new VPRequest();
        credentials.addRequestCredential(VPRequest.emfisisCredential);
        credentials.addVCPolicy(VPRequest.expired);
        String json = gson.toJson(credentials);
        ServerResponse serverResponse = RestApiClient.sendPOST(verifierUri + "/openid4vc/verify", json, Optional.empty());
        return new Response<>(serverResponse, serverResponse.getEntity());
    }
}
