package com.emfisis.vcportal;

import org.springframework.stereotype.Component;

@Component
public class BackendCommunication {

    public String createVerificationUrl(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "https://pickslot.io/";
    }

    public String createIssueingLink(String username){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "https://pickslot.io/";
    }
}
