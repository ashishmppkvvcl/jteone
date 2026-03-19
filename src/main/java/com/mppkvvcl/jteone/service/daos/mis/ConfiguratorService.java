package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.misdao.daos.ConfiguratorDAO;
import com.mppkvvcl.misdao.interfaces.ConfiguratorInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("misConfiguratorService")
public class ConfiguratorService {
    private static final Logger log = LoggerFactory.getLogger(ConfiguratorService.class);
    private static final Map<String, String> configMap = new HashMap<>();

    @Autowired
    @Qualifier("misConfiguratorDAO")
    private ConfiguratorDAO configuratorDAO;

    public ConfiguratorInterface getByPropertyName(String propertyName) {
        if (StringUtils.isEmpty(propertyName)) return null;

        final ConfiguratorInterface configurator = configuratorDAO.getByPropertyName(propertyName);
        if (configurator != null && !configMap.containsKey(propertyName)) {
            configMap.put(propertyName, configurator.getPropertyValue());
        }
        return configurator;
    }

    public String getValueByPropertyName(String propertyName) {
        if (StringUtils.isEmpty(propertyName)) return null;

        if (!configMap.containsKey(propertyName)) {
            final ConfiguratorInterface configurator = configuratorDAO.getByPropertyName(propertyName);
            if (configurator != null) configMap.put(propertyName, configurator.getPropertyValue());
        }
        return configMap.get(propertyName);
    }
}
