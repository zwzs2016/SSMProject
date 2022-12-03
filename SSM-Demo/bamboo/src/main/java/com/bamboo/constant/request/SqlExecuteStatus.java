package com.bamboo.constant.request;

public enum SqlExecuteStatus {
    INSERT_SUCCESS(1,"record inserted successfully"),

    INSERT_FAIL(0,"record inserted fail");

    private int value;
    private String msg;

    SqlExecuteStatus(int value, String msg) {
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
