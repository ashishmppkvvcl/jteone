package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.util.List;

public class MeterReplacementInformation {

    private List<MeterReplacementDetail> meterReplacementDetailList;

    private List<NetMeterReplacementDetail> netMeterReplacementDetailList;

    public List<MeterReplacementDetail> getMeterReplacementDetailList() {
        return meterReplacementDetailList;
    }

    public void setMeterReplacementDetailList(List<MeterReplacementDetail> meterReplacementDetailList) {
        this.meterReplacementDetailList = meterReplacementDetailList;
    }

    public List<NetMeterReplacementDetail> getNetMeterReplacementDetailList() {
        return netMeterReplacementDetailList;
    }

    public void setNetMeterReplacementDetailList(List<NetMeterReplacementDetail> netMeterReplacementDetailList) {
        this.netMeterReplacementDetailList = netMeterReplacementDetailList;
    }

    @Override
    public String toString() {
        return "MeterReplacementInformation{" +
                "meterReplacementDetailList=" + meterReplacementDetailList +
                ", netMeterReplacementDetailList=" + netMeterReplacementDetailList +
                '}';
    }
}
