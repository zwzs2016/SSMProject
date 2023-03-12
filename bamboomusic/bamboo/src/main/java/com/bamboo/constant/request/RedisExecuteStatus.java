package com.bamboo.constant.request;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public enum RedisExecuteStatus {
    INSERT_SUCCESS(1,"Successfully to add cache record"),

    INSERT_FAIL(0,"Failed to add cache record"),

    DELETE_SUCCESS(1,"Successfully deleted cache record"),

    DELETE_FAIL(1,"Failed to delete cache record"),

    NO_VALUE_EXISTS(-1,"record cannot be queried");

    private int value;
    private String msg;

    RedisExecuteStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static RedisExecuteStatus valueOf(int statusCode) {
        RedisExecuteStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    @Nullable
    public static RedisExecuteStatus resolve(int statusCode) {
        for (RedisExecuteStatus status : values()) {
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
