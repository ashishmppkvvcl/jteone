package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.configuration.JteConfiguration;
import com.mppkvvcl.jteone.interfaces.JteTemplateInterface;
import gg.jte.TemplateEngine;
import gg.jte.TemplateException;
import gg.jte.output.Utf8ByteOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JteTemplateService {
    private static final Logger log = LoggerFactory.getLogger(JteTemplateService.class);

    @Autowired
    private JteConfiguration jteConfiguration;

    public byte[] processTemplate(final String subdirectory, final String templateName, final String purpose, final JteTemplateInterface jteTemplate) {
        if (jteTemplate == null || StringUtils.isEmpty(templateName)) return null;

        String path = "";
        if (StringUtils.isNotEmpty(subdirectory)) {
            path = path.concat(subdirectory);
        }
        path = path.concat("/").concat(StringUtils.capitalize(templateName.toLowerCase()));
        if (StringUtils.isNotEmpty(purpose)) {
            path = path.concat(StringUtils.capitalize(purpose.toLowerCase())).concat(".class");
        }

        final Utf8ByteOutput output = new Utf8ByteOutput();
        try {
            final TemplateEngine templateEngine = jteConfiguration.getTemplateEngine();
            templateEngine.render(path, jteTemplate, output);
        } catch (TemplateException templateException) {
            log.error("Template exception occurred", templateException);
        }

        return output.toByteArray();
    }
}
