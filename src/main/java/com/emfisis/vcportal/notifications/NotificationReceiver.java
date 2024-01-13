package com.emfisis.vcportal.notifications;

import com.emfisis.vcportal.views.verify.VerifyView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

@Route("broadcaster")
public class NotificationReceiver extends Div {
    Registration broadcasterRegistration;


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(vpNotification -> {
            ui.access(() -> {
                if(vpNotification.getSessionId().equals(VaadinSession.getCurrent().getSession().getId())){
                    VaadinSession.getCurrent().getAttribute(UiNotifiable.class).uiNotify(vpNotification);
                }
            });
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}
