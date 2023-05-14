package com.uwan.common.constant;

import org.springframework.lang.Nullable;

public enum ProductResults {
    PRODUCT_NOT_ENOUGH(-1,"INSUFFICIENT INVENTORY"),

    PRODUCT_REORDER(-2,"REORDER THE PRODUCT"),

    PRODUCT_ORDER_SAVE(1,"PRODUCT ORDER SUCCESSFULLY PLACED"),

    PRODUCT_ORDER_FAIL(0,"PRODUCT ORDER FAIL");

    private int value;
    private String msg;

    ProductResults(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static ProductResults valueOf(int statusCode) {
        ProductResults status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    @Nullable
    public static ProductResults resolve(int statusCode) {
        for (ProductResults status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
