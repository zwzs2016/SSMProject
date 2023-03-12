package com.bamboo.search.constant.request;

import org.springframework.lang.Nullable;

public enum ElasticsearchExecuteStatus {
    INSERT_SUCCESS(1,"Successfully to add search record"),

    INSERT_FAIL(0,"Failed to add search record"),

    DELETE_SUCCESS(1,"Successfully deleted search record"),

    DELETE_FAIL(1,"Failed to delete search record"),

    NO_VALUE_EXISTS(-1,"record cannot be queried");

    private int value;
    private String msg;

    ElasticsearchExecuteStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static ElasticsearchExecuteStatus valueOf(int statusCode) {
        ElasticsearchExecuteStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    @Nullable
    public static ElasticsearchExecuteStatus resolve(int statusCode) {
        for (ElasticsearchExecuteStatus status : values()) {
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
