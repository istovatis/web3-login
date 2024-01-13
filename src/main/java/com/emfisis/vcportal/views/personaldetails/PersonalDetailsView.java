package com.emfisis.vcportal.views.personaldetails;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@PageTitle("Personal Details")
@Route(value = "personal-details")
@Uses(Icon.class)
public class PersonalDetailsView extends Composite<VerticalLayout> {

    Button needHelp = new Button("Need help?");

    HorizontalLayout main = new HorizontalLayout();
    HorizontalLayout layoutRow = new HorizontalLayout();

    MessageList helpMessages = new MessageList();

    VerticalLayout leftColumn = new VerticalLayout();
    VerticalLayout rightColumn = new VerticalLayout();


    public PersonalDetailsView() {
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        main.setWidth("100%");
        main.setMaxWidth("800px");
        main.setHeight("min-content");
        main.setJustifyContentMode(JustifyContentMode.START);
        main.setAlignItems(Alignment.START);
        h3.setText("Personal Information");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Username");
        textField.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Issue Card");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().setAttribute("username", textField.getValue());
            UI.getCurrent().navigate("issue");
        });
        getContent().add(main);
        leftColumn.setWidth("50%");
        main.add(leftColumn, rightColumn);
        leftColumn.add(h3);
        leftColumn.add(formLayout2Col);
        formLayout2Col.add(textField);

        needHelp.addClickListener(this::addHelpMessages);
        leftColumn.add(needHelp);
        leftColumn.add(layoutRow);
        layoutRow.add(buttonPrimary);
    }

    private void addHelpMessages(ClickEvent<Button> event) {
        H3 h3 = new H3("What is Verifiable Credential issuance?");
        needHelp.setEnabled(false);
        MessageListItem message1 = new MessageListItem(
                "Here you can get your 'Emfisis' Login card.",
                now(), "Step 1");
        MessageListItem message2 = new MessageListItem(
                "Just put your username and click on the 'Issue Card' button. A new 'Emfisis' Login card will be issued for you.",
                now(), "Step 2");
        message2.setUserColorIndex(6);

        helpMessages.setItems(message1, message2);

        rightColumn.add(h3);
        rightColumn.add(helpMessages);

    }

    private Instant now() {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

}
