package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.configuration.HttpConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class WkHtmlToPdfService {
    private static final Logger log = LoggerFactory.getLogger(WkHtmlToPdfService.class);

    @Value("${html.pdf.converter.api}")
    private String apiUrl;

    @Autowired
    private HttpConfiguration httpConfiguration;

    @Retryable(includes = {Exception.class}, delay = 1000, maxRetries = 1)
    public byte[] getPdfFromHtml(byte[] htmlContent) throws Exception {
        if (htmlContent == null) return null;

        byte[] out = null;
        try {
            out = httpConfiguration.getRestTemplate().postForObject(apiUrl, htmlContent, byte[].class);
        } catch (Exception exception) {
            log.error("Exception occurred while calling WK Html to Pdf Conversion API", exception);
            throw new Exception(exception);
        }

        return out;
    }
}
