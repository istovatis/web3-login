package com.emfisis.vcportal.views.verify;

import com.emfisis.vcportal.BackendCommunication;
import com.emfisis.vcportal.notifications.Broadcaster;
import com.emfisis.vcportal.notifications.NotificationReceiver;
import com.emfisis.vcportal.notifications.UiNotifiable;
import com.emfisis.vcportal.notifications.VpNotification;
import com.emfisis.vcportal.utils.QrcodeImageCreator;
import com.emfisis.vcportal.utils.RestCalling.Response;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@PageTitle("Verify")
@Route(value = "verify")
@Uses(Icon.class)
public class VerifyView extends Composite<VerticalLayout> implements UiNotifiable {

    Logger logger = LoggerFactory.getLogger(VerifyView.class);

    HorizontalLayout main = new HorizontalLayout();
    VerticalLayout leftColumn;
    VerticalLayout rightColumn = new VerticalLayout();

    MessageList helpMessages = new MessageList();
    Button needHelp = new Button("Need help?");

    BackendCommunication backendCommunication;

    Response<String> verificationUrl;

    public VerifyView(BackendCommunication backendCommunication) {
        this.backendCommunication = backendCommunication;
        leftColumn = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        main.setWidth("100%");
        main.setMaxWidth("800px");
        main.setHeight("min-content");
        h3.setText("Scan with web3 wallet to login");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Get a new Card");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("personal-details");
        });
        needHelp.addClickListener(this::addHelpMessages);
        NotificationReceiver notificationReceiver = new NotificationReceiver();
        notificationReceiver.setVisible(false);
        getContent().add(notificationReceiver);
        getContent().add(main);
        leftColumn.setWidth("50%");
        main.add(leftColumn, rightColumn);
        leftColumn.add(h3);
        leftColumn.add(formLayout2Col);
        System.out.println(VaadinSession.getCurrent().getSession().getId());
        verificationUrl = backendCommunication.createVerificationUrl(VaadinSession.getCurrent().getSession().getId());
        if (verificationUrl.serverResponse.getStatus() == 200) {
            QrcodeImageCreator qrCodeImageCreator = new QrcodeImageCreator(verificationUrl.entity);
            Image qrCodeImage = qrCodeImageCreator.getImage();
            qrCodeImage.setWidth("100%");
            leftColumn.add(qrCodeImage);
            leftColumn.add(needHelp);
            layoutRow.add(buttonPrimary);
            leftColumn.add(layoutRow);
        }
        VaadinSession.getCurrent().setAttribute(UiNotifiable.class, this);
    }

    private void addHelpMessages(ClickEvent<Button> event) {
        H3 h3 = new H3("What is Web3 Login?");
        needHelp.setEnabled(false);
        MessageListItem message1 = new MessageListItem(
                "No username/password is needed to login.",
                now(), "Step 1");

        MessageListItem message2 = new MessageListItem(
                "You can use any web3 wallet. (Under the hood, the wallet must implement the OIDC4VC/OIDC4VP protocol standards).",
                now(), "Step 2");
        MessageListItem message3 = new MessageListItem(
                "Open the wallet and scan the QR Code above.",
                now(), "Step 3");
        MessageListItem message4 = new MessageListItem(
                "You must have an 'Emfisis' login card issued at your wallet. Present this card, and the login is over!",
                now(), "Step 4");
        message4.setUserColorIndex(6);

        MessageListItem message5 = new MessageListItem(
                " If you don't have such login card, click on the 'Get a new Card' button to get a fresh 'Emfisis' Login card.",
                now(), "Step 5");

        helpMessages.setItems(message1, message2, message3, message4, message5);

        rightColumn.add(h3);
        rightColumn.add(helpMessages);

    }

    private Instant now() {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }


    @Override
    public void uiNotify(VpNotification vpNotification) {
        if(vpNotification.isValidVp()){
            Notification notification = Notification
                    .show("Congrats the validation of your cv completed successfully");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }else{
            Notification notification = Notification
                    .show("Sorry but the validation of your vc failed");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
