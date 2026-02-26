package com.mppkvvcl.jteone.configuration;

//TODO:import gg.jte.generated.precompiled.JteBillEnglishGenerated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.stereotype.Component;

@Component
public class ResourceRuntimeHints implements RuntimeHintsRegistrar {

    private static final Logger log = LoggerFactory.getLogger(ResourceRuntimeHints.class);

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        log.info("Registering hints for resources");

        hints.resources().registerPattern("**/*.bin");

        //TODO:hints.reflection().registerType(JteBillEnglishGenerated.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);
    }
}
