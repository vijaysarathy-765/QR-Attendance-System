package com.app.attendance.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

@Component
public class QRCodeGenerator {

    public byte[] generateQRCode(String text, int width, int height)
            throws Exception {

        BitMatrix matrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height);

        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                image.setRGB(
                        x,
                        y,
                        matrix.get(x, y)
                                ? 0xFF000000
                                : 0xFFFFFFFF);
            }
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(image, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}