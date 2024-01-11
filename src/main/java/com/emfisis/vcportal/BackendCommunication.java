package com.emfisis.vcportal;

import com.emfisis.vcportal.data.IssuanceVCRequest;
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

    private static Gson gson = new Gson();

    public String createVerificationUrl() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("verifing vc");
        return "openid4vp://authorize?response_type=vp_token&client_id=http%3A%2F%2Fverifier-env.eba-6jbe9znz.eu-central-1.elasticbeanstalk.com%2F%2Fopenid4vc%2Fverify&response_mode=direct_post&state=ceed87d6-1f5f-4637-9a7a-9177062cca92&presentation_definition=%7B%22input_descriptors%22%3A%5B%7B%22id%22%3A%22Emfisis%22%2C%22format%22%3A%7B%22jwt_vc_json%22%3A%7B%22alg%22%3A%5B%22EdDSA%22%5D%7D%7D%2C%22constraints%22%3A%7B%22fields%22%3A%5B%7B%22path%22%3A%5B%22%24.type%22%5D%2C%22filter%22%3A%7B%22type%22%3A%22string%22%2C%22pattern%22%3A%22Emfisis%22%7D%7D%5D%7D%7D%5D%7D&client_id_scheme=redirect_uri&response_uri=http%3A%2F%2Fverifier-env.eba-6jbe9znz.eu-central-1.elasticbeanstalk.com%2F%2Fopenid4vc%2Fverify%2Fceed87d6-1f5f-4637-9a7a-9177062cca92";
    }

    public Response<String> createIssuingLink(String username) {
        IssuanceVCRequest vc = new IssuanceVCRequest(issuerKey, issuerDID, username);
        logger.info("Issuing VC: KEY:" + vc.getIssuanceKey().getJwk() + "  DID:" + vc.getIssuerDid() + " Username:" + vc.getUsername());

        Response<String> issueResponse = issueVC(vc);
        logger.info("Issuing VC Response: " + issueResponse.serverResponse.getStatus() + " error: " + issueResponse.serverResponse.getError());
        return issueResponse;
    }

    public Response<String> issueVC(IssuanceVCRequest vc) {
        String json = gson.toJson(vc);
        ServerResponse serverResponse = RestApiClient.sendPOST(issuerUri + "/openid4vc/jwt/issue-emfisis", json, Optional.empty());
        return new Response<>(serverResponse, serverResponse.getEntity());
    }
    private String getEnvOrDefault(String property, String defaultValue) {
        String value = System.getenv(property);
        if (value == null || value.trim().equals("")) {
            value = defaultValue;
        }
        return value;
    }
}
