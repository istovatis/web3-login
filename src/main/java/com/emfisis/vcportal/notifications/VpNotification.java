package com.emfisis.vcportal.notifications;

public class VpNotification {
    String sessionId;
    boolean validVp;

    public VpNotification(String sessionId, boolean validVp) {
        this.sessionId = sessionId;
        this.validVp = validVp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isValidVp() {
        return validVp;
    }

    public void setValidVp(boolean validVp) {
        this.validVp = validVp;
    }
}
