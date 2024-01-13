package com.emfisis.vcportal.views.verify;

import com.emfisis.vcportal.BackendCommunication;
import com.emfisis.vcportal.notifications.Broadcaster;
import com.emfisis.vcportal.notifications.VpNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification-callback-api")
public class VerificationCallbacksApi {

    Logger logger = LoggerFactory.getLogger(VerificationCallbacksApi.class);

    @GetMapping("/success/{id}")
    public void verificationSuccess(@PathVariable(value = "id") String sessionId){
        Broadcaster.broadcast(new VpNotification(sessionId,true));
    }

    @GetMapping("/fail/{id}")
    public void verificationFail(@PathVariable(value = "id") String sessionId){
        Broadcaster.broadcast(new VpNotification(sessionId,false));
    }
}
