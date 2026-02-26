package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NetMeterReplacementDetail {

    private int index;

    private String meterDetail;
    private LocalDate replacementDate;
    private BigDecimal importPreviousRead;
    private BigDecimal importCurrentRead;
    private BigDecimal importConsumption;
    private BigDecimal exportPreviousRead;
    private BigDecimal exportCurrentRead;
    private BigDecimal exportConsumption;
    private BigDecimal netConsumption;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMeterDetail() {
        return meterDetail;
    }

    public void setMeterDetail(String meterDetail) {
        this.meterDetail = meterDetail;
    }

    public LocalDate getReplacementDate() {
        return replacementDate;
    }

    public void setReplacementDate(LocalDate replacementDate) {
        this.replacementDate = replacementDate;
    }

    public BigDecimal getImportPreviousRead() {
        return importPreviousRead;
    }

    public void setImportPreviousRead(BigDecimal importPreviousRead) {
        this.importPreviousRead = importPreviousRead;
    }

    public BigDecimal getImportCurrentRead() {
        return importCurrentRead;
    }

    public void setImportCurrentRead(BigDecimal importCurrentRead) {
        this.importCurrentRead = importCurrentRead;
    }

    public BigDecimal getImportConsumption() {
        return importConsumption;
    }

    public void setImportConsumption(BigDecimal importConsumption) {
        this.importConsumption = importConsumption;
    }

    public BigDecimal getExportPreviousRead() {
        return exportPreviousRead;
    }

    public void setExportPreviousRead(BigDecimal exportPreviousRead) {
        this.exportPreviousRead = exportPreviousRead;
    }

    public BigDecimal getExportCurrentRead() {
        return exportCurrentRead;
    }

    public void setExportCurrentRead(BigDecimal exportCurrentRead) {
        this.exportCurrentRead = exportCurrentRead;
    }

    public BigDecimal getExportConsumption() {
        return exportConsumption;
    }

    public void setExportConsumption(BigDecimal exportConsumption) {
        this.exportConsumption = exportConsumption;
    }

    public BigDecimal getNetConsumption() {
        return netConsumption;
    }

    public void setNetConsumption(BigDecimal netConsumption) {
        this.netConsumption = netConsumption;
    }

    @Override
    public String toString() {
        return "NetMeterReplacementDetail{" +
                "index=" + index +
                ", meterDetail='" + meterDetail + '\'' +
                ", replacementDate=" + replacementDate +
                ", importPreviousRead=" + importPreviousRead +
                ", importCurrentRead=" + importCurrentRead +
                ", importConsumption=" + importConsumption +
                ", exportPreviousRead=" + exportPreviousRead +
                ", exportCurrentRead=" + exportCurrentRead +
                ", exportConsumption=" + exportConsumption +
                ", netConsumption=" + netConsumption +
                '}';
    }
}
