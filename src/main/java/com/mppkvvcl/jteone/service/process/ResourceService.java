package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.utility.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ResourceService {
    private static final Logger log = LoggerFactory.getLogger(ResourceService.class);

    public static final String IMAGE_TYPE_LOGO = "logo";
    public static final String IMAGE_TYPE_QR = "qr";
    public static final String IMAGE_EXTENSION_PNG = "png";

    @Value("classpath:images")
    private String imagePrefix;

    public URL getImage(final String imageName) {

        URL url = null;
        if (StringUtils.isNotEmpty(imageName)) {
            String path = imagePrefix + "/" + imageName;
            final String misResources = System.getenv(GlobalConstant.MIS_JTE_RESOURCES);
            if (StringUtils.isNotEmpty(misResources)) {
                path = misResources + "/" + path.replaceAll("classpath:", "");
            }
            if (StringUtils.isNotEmpty(path)) {
                try {
                    url = ResourceUtils.getURL(path);
                } catch (Exception exception) {
                    log.error("Error while getting URL: {}", imageName, exception);
                }
            }
        }
        return url;
    }

    public URL getImage(final String imageName, final String imageType) {

        URL url = null;
        if (StringUtils.isNotEmpty(imageName)) {
            String path = (StringUtils.isNotEmpty(imageType)) ? imagePrefix + "/" + imageType + "/" + imageName : imagePrefix + "/" + imageName;
            final String misResources = System.getenv(GlobalConstant.MIS_JTE_RESOURCES);
            if (StringUtils.isNotEmpty(misResources)) {
                path = misResources + "/" + path.replaceAll("classpath:", "");
            }
            if (StringUtils.isNotEmpty(path)) {
                try {
                    url = ResourceUtils.getURL(path);
                } catch (Exception exception) {
                    log.error("Error while getting URL: {}", imageName, exception);
                }
            }
        }
        return url;
    }

    public String convertPngToBase64(URL url) {
        byte[] imageBytes = null;
        try {
            final Path path = Paths.get(url.toURI());

            imageBytes = Files.readAllBytes(path);
        } catch (IOException | URISyntaxException exception) {
            log.error("Error while reading File: {}", url, exception);
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
