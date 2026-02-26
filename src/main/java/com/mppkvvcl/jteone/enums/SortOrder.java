package com.mppkvvcl.jteone.enums;

public enum SortOrder {
    ASC("ASCENDING"),
    DESC("DESCENDING");

    private final String order;

    SortOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }
}
