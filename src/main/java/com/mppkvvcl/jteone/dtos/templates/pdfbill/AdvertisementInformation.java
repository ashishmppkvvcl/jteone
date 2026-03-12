package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.util.List;

public class AdvertisementInformation {

    private String pageOneZeroBase64Image;

    private String pageTwoOneBase64Image;

    private List<String> lastPageBase64Image;

    public String getPageOneZeroBase64Image() {
        return pageOneZeroBase64Image;
    }

    public void setPageOneZeroBase64Image(String pageOneZeroBase64Image) {
        this.pageOneZeroBase64Image = pageOneZeroBase64Image;
    }

    public String getPageTwoOneBase64Image() {
        return pageTwoOneBase64Image;
    }

    public void setPageTwoOneBase64Image(String pageTwoOneBase64Image) {
        this.pageTwoOneBase64Image = pageTwoOneBase64Image;
    }

    public List<String> getLastPageBase64Image() {
        return lastPageBase64Image;
    }

    public void setLastPageBase64Image(List<String> lastPageBase64Image) {
        this.lastPageBase64Image = lastPageBase64Image;
    }
}
