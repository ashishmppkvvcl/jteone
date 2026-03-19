package com.mppkvvcl.jteone.service.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HtmlToPdfService {
    private static final Logger log = LoggerFactory.getLogger(HtmlToPdfService.class);

    @Autowired
    private WkHtmlToPdfService wkHtmlToPdfService;

    @Autowired
    private PlayWrightService playWrightService;

    @Value("${html.pdf.vendor}")
    private String htmlToPdfConversionVendor;

    public byte[] getPdfFromHtml(byte[] htmlContent) {
        if (htmlContent == null || htmlToPdfConversionVendor == null) return null;

        byte[] out = null;
        if ("WKHtmlToPdf".equalsIgnoreCase(htmlToPdfConversionVendor)) {
            try {
                out = wkHtmlToPdfService.getPdfFromHtml(htmlContent);

            } catch (Exception exception) {
                log.error("ERROR ALERT:: AFTER RETRY:: Exception occurred while converting HTML to PDF", exception);
            }
        }
        if ("PlayWright".equalsIgnoreCase(htmlToPdfConversionVendor)) {
            try {
                out = playWrightService.getPdfFromHtml(htmlContent);

            } catch (Exception exception) {
                log.error("ERROR ALERT:: AFTER RETRY:: Exception occurred while converting HTML to PDF", exception);
            }
        } else {
            log.error("Invalid HTML to PDF Conversion Vendor {}", htmlToPdfConversionVendor);
        }

        return out;
    }
}
