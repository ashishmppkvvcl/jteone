package com.mppkvvcl.jteone.configuration;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JteConfiguration {

    TemplateEngine templateEngine;

    @PostConstruct
    public void init() {
        templateEngine = TemplateEngine.createPrecompiled(ContentType.Html);
        templateEngine.setBinaryStaticContent(true);
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void cleanUp() {
        templateEngine.clearCache();
        templateEngine.cleanAll();
    }
}
