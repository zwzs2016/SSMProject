package com.uwan.common.constant;

public enum SqlExecuteStatus {
    INSERT_SUCCESS(1,"record inserted successfully"),

    INSERT_FAIL(0,"record inserted fail"),

    DELETE_SUCCESS(1,"record delete successfully"),

    DELETE_FAIL(0,"record delete fail");

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
