package com.emfisis.vcportal.views.issue;

import com.emfisis.vcportal.BackendCommunication;
import com.emfisis.vcportal.utils.QrcodeImageCreator;
import com.emfisis.vcportal.utils.RestCalling.Response;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.olli.ClipboardHelper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@PageTitle("Issue")
@Route(value = "issue")
@Uses(Icon.class)
public class IssueView extends Composite<VerticalLayout> {

    Logger logger = LoggerFactory.getLogger(IssueView.class);

    BackendCommunication backendCommunication;

    HorizontalLayout layoutRow = new HorizontalLayout();

    HorizontalLayout main = new HorizontalLayout();
    VerticalLayout rightColumn = new VerticalLayout();

    VerticalLayout leftColumn = new VerticalLayout();

    VerticalLayout helpLayout = new VerticalLayout();

    MessageList helpMessages = new MessageList();
    Button needHelp = new Button("Need help?");

    Response<String> response;

    Button copyButton = new Button("Copy Card Url");

    public IssueView(BackendCommunication backendCommunication) {
        this.backendCommunication = backendCommunication;
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        main.setWidth("100%");
        main.setMaxWidth("800px");
        main.setHeight("min-content");
        buttonPrimary.setText("Login with Web3");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("verify");
        });
        getContent().add(main);

        leftColumn.setWidth("60%");
        main.add(leftColumn, rightColumn);
        h3.setText("Scan with Web3 wallet to get it");
        h3.setWidth("100%");
        leftColumn.add(h3);
        leftColumn.add(formLayout2Col);

        HorizontalLayout otherWallet = new HorizontalLayout();
        otherWallet.setWidthFull();
        Anchor anchor = new Anchor();
        anchor.setHref("https://wallet.walt.id/login");
        anchor.setText("Walt.id web wallet");
        anchor.setTarget("_blank");  // Specify `_blank` to open in a new browser tab/window.

        copyButton = new Button("Copy Card");
        ClipboardHelper clipboardHelper = new ClipboardHelper("card url", copyButton);
        copyButton.addClickListener(event -> clipBoardClicked(event));
        needHelp.addClickListener(this::addHelpMessages);
        otherWallet.setAlignItems(Alignment.CENTER);

        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        response = backendCommunication.createIssuingLink((String) VaadinSession.getCurrent().getAttribute("username"));

        if (response.serverResponse.getStatus() == 200) {
            QrcodeImageCreator qrCodeImageCreator = new QrcodeImageCreator(response.entity);
            leftColumn.add(qrCodeImageCreator.getImage());
            clipboardHelper.setContent(response.entity);
            helpLayout.add(clipboardHelper, needHelp);
            leftColumn.add(helpLayout);
            leftColumn.add(anchor);
            layoutRow.add(buttonPrimary);
            leftColumn.add(layoutRow);

        } else {
            logger.info("Not showing the QR code: " + response.serverResponse.getError());
        }

    }

    private void clipBoardClicked(ClickEvent<Button> event) {
        copyButton.setText("Copied");
    }

    private void addHelpMessages(ClickEvent<Button> event) {
        H3 h3 = new H3("How to use the walt.id web wallet");
        needHelp.setEnabled(false);
        MessageListItem message1 = new MessageListItem(
                "If you have a web3 wallet, just scan the QR code.",
                now(), "Installed Wallet");

        MessageListItem message2 = new MessageListItem(
                "Otherwise, click on the link to open the walt.id web wallet and signup/login.",
                now(), "Walt.id wallet");
        MessageListItem message3 = new MessageListItem(
                "Click on 'Select Wallet' at the left tab and then at the 'View Wallet'.",
                now(), "Step 1");
        MessageListItem message4 = new MessageListItem(
                "Click on 'Credentials' at the left tab and then 'Scan to receive or present credentials.' ",
                now(), "Step 2");
        MessageListItem message5 = new MessageListItem(
                "Click on the 'Copy Card' button. ",
                now(), "Step 3");
        MessageListItem message6 = new MessageListItem(
                "Paste the copied text to the 'request string'. ",
                now(), "Step 4");
        MessageListItem message7 = new MessageListItem(
                "The Login card has been issued to your wallet!",
                now(), "Done!");
        message7.setUserColorIndex(6);
        helpMessages.setItems(message1, message2, message3, message4, message5, message6, message7);

        rightColumn.add(h3);
        rightColumn.add(helpMessages);

    }

    private Instant now() {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
}
