package com.emfisis.vcportal.views.issue;

import com.emfisis.vcportal.BackendCommunication;
import com.emfisis.vcportal.utils.QrcodeImageCreator;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Issue")
@Route(value = "issue")
@Uses(Icon.class)
public class IssueView extends Composite<VerticalLayout> {

    BackendCommunication backendCommunication;

    public IssueView(BackendCommunication backendCommunication) {
        this.backendCommunication = backendCommunication;
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Scan with Web3 wallet to get it");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Login with Web3");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("verify");
        });
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        QrcodeImageCreator qrCodeImageCreator = new QrcodeImageCreator(backendCommunication.createIssueingLink("frank"));

        layoutColumn2.add(qrCodeImageCreator.getImage());
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
    }
}
