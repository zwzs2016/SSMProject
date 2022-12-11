package com.bamboo.constant.request;

public enum RedisExecuteStatus {
    INSERT_SUCCESS(1,"Successfully to add cache record"),

    INSERT_FAIL(0,"Failed to add cache record"),

    DELETE_SUCCESS(1,"Successfully deleted cache record"),

    DELETE_FAIL(1,"Failed to delete cache record");

    private int value;
    private String msg;

    RedisExecuteStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
