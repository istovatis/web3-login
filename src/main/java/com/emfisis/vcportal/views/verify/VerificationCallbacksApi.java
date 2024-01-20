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
    public String verificationSuccess(@PathVariable(value = "id") String sessionId){
        Broadcaster.broadcast(new VpNotification(sessionId,true));
        return "" +
                "<style>.correct { " +
                "background-color: green;"+
                "padding: 57px;"+
                "border-radius: 60px;"+
                "margin-left: 40%;"+
                "margin-top: 100px;"+
                "position: absolute;"+
                "color: white;"+
                "font-size: 106;"+
                "}" +
                "</style>" +
                "<span class=\"correct\">&#10003;</span>";
    }
// html example

    @GetMapping("/fail/{id}")
    public String verificationFail(@PathVariable(value = "id") String sessionId){
        Broadcaster.broadcast(new VpNotification(sessionId,false));
        return "" +
                "<style>.wrong { " +
                "background-color: red;"+
                "padding: 57px;"+
                "border-radius: 60px;"+
                "margin-left: 40%;"+
                "margin-top: 100px;"+
                "position: absolute;"+
                "color: white;"+
                "font-size: 106;"+
                "}" +
                "</style>" +
                "<span class=\"wrong\">&#9785;</span>";
    }
}
