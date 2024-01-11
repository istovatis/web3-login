package com.emfisis.vcportal.views.verify;

import com.emfisis.vcportal.BackendCommunication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification-callback-api")
public class VerificationCallbacksApi {

    Logger logger = LoggerFactory.getLogger(VerificationCallbacksApi.class);

    @GetMapping("/success")
    public void verificationSuccess(){
        logger.info("verification finished successfully");
    }

    @GetMapping("/fail")
    public void verificationFail(){
        logger.info("verification failed");
    }
}
