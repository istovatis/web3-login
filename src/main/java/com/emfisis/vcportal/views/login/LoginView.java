package com.emfisis.vcportal.views.login;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Login")
@Route(value = "login")
@RouteAlias(value = "")
@Uses(Icon.class)
public class LoginView extends Composite<VerticalLayout> {

    public LoginView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Button web3Loginbutton = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.getStyle().set("flex-grow", "1");
        web3Loginbutton.setText("Login with web3");
        web3Loginbutton.setWidth("100%");
        web3Loginbutton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        web3Loginbutton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("verify");
        });
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        getContent().add(layoutColumn2);
        layoutColumn2.add(web3Loginbutton);
        layoutColumn2.add(layoutRow);
    }
}
