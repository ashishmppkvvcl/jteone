package com.mppkvvcl.jteone.service.process;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRService {
    private static final Logger log = LoggerFactory.getLogger(QRService.class);

    public String getAsBase64Image(final String data, final int width, final int height) {
        if (StringUtils.isEmpty(data) || width == 0 | height == 0) return null;

        String base64String = null;
        try {
            final BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, ResourceService.IMAGE_EXTENSION_PNG, bos);
            base64String = ResourceService.PNG_HTML_BASE54_PREFIX + Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (WriterException | IOException e) {
            log.error("Error occurred while generating payment QR for {}", data);
        }

        return base64String;
    }
}

