package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.jteone.service.process.ResourceService;
import com.mppkvvcl.jteone.utility.GlobalConstant;
import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.misdao.daos.DiscomDAO;
import com.mppkvvcl.misdao.interfaces.DiscomInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("misDiscomService")
public class DiscomService {

    private static final Logger log = LoggerFactory.getLogger(DiscomService.class);

    private static DiscomInterface discom;
    private static String discomLogoAsBase64;
    private static String mpstateLogoAsBase64;
    private static String whatsappQRAsBase64;
    private static String imageExtension;
    private static String imageHtmlPrefix;

    @Autowired
    @Qualifier("misDiscomDAO")
    private DiscomDAO discomDAO;

    @Autowired
    private ResourceService resourceService;

    public List<? extends DiscomInterface> getAll() {

        return discomDAO.getAll();
    }

    public DiscomInterface get() {
        if (discom != null) return discom;

        final List<? extends DiscomInterface> discomList = getAll();
        if (!GlobalUtil.isEmpty(discomList)) discom = discomList.getFirst();

        return discom;
    }

    public String getDiscomLogoAsBase64() {
        if (discomLogoAsBase64 != null) return discomLogoAsBase64;

        final DiscomInterface discom = get();
        if (discom == null) return null;
        if (imageExtension == null)
            imageExtension = (GlobalConstant.DISCOM_MPWZ.equalsIgnoreCase(discom.getShortName())) ? ResourceService.IMAGE_EXTENSION_SVG : ResourceService.IMAGE_EXTENSION_PNG;
        if (imageHtmlPrefix == null)
            imageHtmlPrefix = (GlobalConstant.DISCOM_MPWZ.equalsIgnoreCase(discom.getShortName())) ? ResourceService.SVG_HTML_BASE54_PREFIX : ResourceService.PNG_HTML_BASE54_PREFIX;

        discomLogoAsBase64 = imageHtmlPrefix + resourceService.convertImageToBase64(resourceService.getImage(discom.getShortName().toLowerCase() + "." + imageExtension, ResourceService.IMAGE_TYPE_LOGO));
        return discomLogoAsBase64;
    }

    public String getMPStateLogoAsBase64() {
        if (mpstateLogoAsBase64 != null) return mpstateLogoAsBase64;

        final DiscomInterface discom = get();
        if (discom == null) return null;
        if (imageExtension == null)
            imageExtension = (GlobalConstant.DISCOM_MPWZ.equalsIgnoreCase(discom.getShortName())) ? ResourceService.IMAGE_EXTENSION_SVG : ResourceService.IMAGE_EXTENSION_PNG;
        if (imageHtmlPrefix == null)
            imageHtmlPrefix = (GlobalConstant.DISCOM_MPWZ.equalsIgnoreCase(discom.getShortName())) ? ResourceService.SVG_HTML_BASE54_PREFIX : ResourceService.PNG_HTML_BASE54_PREFIX;

        mpstateLogoAsBase64 = imageHtmlPrefix + resourceService.convertImageToBase64(resourceService.getImage("state_mp." + imageExtension, ResourceService.IMAGE_TYPE_LOGO));
        return mpstateLogoAsBase64;
    }

    public String getWhatsappQRAsBase64() {
        if (whatsappQRAsBase64 != null) return whatsappQRAsBase64;

        final DiscomInterface discom = get();
        if (discom == null) return null;

        whatsappQRAsBase64 = ResourceService.PNG_HTML_BASE54_PREFIX + resourceService.convertImageToBase64(resourceService.getImage("whatsapp_" + discom.getShortName().toLowerCase() + "." + ResourceService.IMAGE_EXTENSION_PNG, ResourceService.IMAGE_TYPE_QR));
        return whatsappQRAsBase64;
    }
}
