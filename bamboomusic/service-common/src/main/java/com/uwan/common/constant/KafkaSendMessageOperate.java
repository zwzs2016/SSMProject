package com.uwan.common.constant;

import org.springframework.lang.Nullable;

public enum KafkaSendMessageOperate {
    ADD_MUSICINFO(0,"add"),

    DELETE_MUSICINFO(-1,"delete"),

    QUERY_MUSICINFO(1,"delete");

    private int value;
    private String msg;

    KafkaSendMessageOperate(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static KafkaSendMessageOperate valueOf(int statusCode) {
        KafkaSendMessageOperate status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    @Nullable
    public static KafkaSendMessageOperate resolve(int statusCode) {
        for (KafkaSendMessageOperate status : values()) {
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
