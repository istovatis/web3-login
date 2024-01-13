package com.emfisis.vcportal.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class QrcodeImageCreator {

    private final String data;

    public QrcodeImageCreator(String data) {
        this.data = data;
    }

    public Image getImage(){
        Image qrCodeImage;
        StreamResource resource = new StreamResource("image.jpg",
                () -> imageToSTream(generateQRCodeImage(data)));
        qrCodeImage = new Image(resource,"Qrcode generating");
        qrCodeImage.setWidth("100%");
        qrCodeImage.getStyle().set("align-self", "center");
        return qrCodeImage;
    }

    private BufferedImage generateQRCodeImage(String barcodeText){
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                null;
        try {
            bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private InputStream imageToSTream(BufferedImage buffImage) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImage, "jpeg", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }
}
