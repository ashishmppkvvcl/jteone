package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class AdjustmentInformation {

    private int index;

    private String description;
    private BigDecimal amount;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AdjustmentInformation() {
    }

    public AdjustmentInformation(int index, String description, BigDecimal amount) {
        this.index = index;
        this.description = description;
        this.amount = amount;
    }
}
