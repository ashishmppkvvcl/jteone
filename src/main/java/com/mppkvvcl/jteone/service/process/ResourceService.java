package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.utility.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResourceService {
    private static final Logger log = LoggerFactory.getLogger(ResourceService.class);

    public static final String IMAGE_TYPE_LOGO = "logo";
    public static final String IMAGE_TYPE_QR = "qr";
    public static final String IMAGE_TYPE_ADVERTISEMENT = "advertisement";
    public static final String IMAGE_TYPE_BILL_ADVERTISEMENT = "advertisement/bill";
    public static final String IMAGE_EXTENSION_PNG = "png";
    public static final String IMAGE_EXTENSION_SVG = "svg";
    public static final String IMAGE_EXTENSION_BMP = "bmp";
    public static final String IMAGE_EXTENSION_JPEG = "jpeg";
    public static final String PNG_HTML_BASE54_PREFIX = "data:image/png;base64,";
    public static final String SVG_HTML_BASE54_PREFIX = "data:image/svg+xml;base64,";
    public static final String BMP_HTML_BASE54_PREFIX = "data:image/bmp;base64,";
    public static final String JPEG_HTML_BASE54_PREFIX = "data:image/jpeg;base64,";

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


    public List<URL> getImageFromDirectoryExcept(final String dirPath, final String imageExtension, final List<String> excludedNames) {
        if (dirPath == null || excludedNames == null) return null;

        final String misResources = System.getenv(GlobalConstant.MIS_JTE_RESOURCES);
        final String refPath = StringUtils.isEmpty(misResources) ? imagePrefix + "/" + dirPath : misResources + "/" + imagePrefix.replaceAll("classpath:", "") + "/" + dirPath;

        List<URL> urls = null;
        final Path dir = Paths.get(refPath);

        try (Stream<Path> stream = Files.list(dir)) {
            urls = stream.filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString)
                    .filter(name -> name.toLowerCase().endsWith(imageExtension) && !excludedNames.contains(name))
                    .map(fileName -> {
                        try {
                            return ResourceUtils.getURL(refPath + "/" + fileName);
                        } catch (FileNotFoundException exception) {
                            log.error("Error while getting files parsing URL: {}", dirPath, exception);
                        }
                        return null;
                    })
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            log.error("Error while getting files from directory: {}", dirPath, exception);
        }
        return urls;
    }

    public String convertImageToBase64(URL url) {
        if (url == null) return null;

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
