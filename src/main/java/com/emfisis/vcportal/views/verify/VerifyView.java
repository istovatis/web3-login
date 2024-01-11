package com.emfisis.vcportal.views.verify;

import com.emfisis.vcportal.BackendCommunication;
import com.emfisis.vcportal.utils.QrcodeImageCreator;
import com.emfisis.vcportal.utils.RestCalling.Response;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@PageTitle("Verify")
@Route(value = "verify")
@Uses(Icon.class)
public class VerifyView extends Composite<VerticalLayout> {

    VerticalLayout mainLayout = new VerticalLayout();


    BackendCommunication backendCommunication;

    public VerifyView(BackendCommunication backendCommunication) {
        this.backendCommunication = backendCommunication;
        mainLayout = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        mainLayout.setWidth("100%");
        mainLayout.setMaxWidth("800px");
        mainLayout.setHeight("min-content");
        h3.setText("Scan with web3 wallet to login");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Don't have one? Get now!");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("personal-details");
        });
        getContent().add(mainLayout);
        mainLayout.add(h3);
        mainLayout.add(formLayout2Col);

        Response<String> verificationUrl = backendCommunication.createVerificationUrl();
        if (verificationUrl.serverResponse.getStatus() == 200) {
            QrcodeImageCreator qrCodeImageCreator = new QrcodeImageCreator(verificationUrl.entity);
            mainLayout.add(qrCodeImageCreator.getImage());
            mainLayout.add(layoutRow);
            layoutRow.add(buttonPrimary);
        }
    }




}
