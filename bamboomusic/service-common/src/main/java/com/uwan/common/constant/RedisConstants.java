package com.uwan.common.constant;

import org.springframework.lang.Nullable;

public enum RedisConstants {
    SECKILL_STOCK_KEY(1,"SECKILL:STOCK:"),
    SECKILL_ORDER_KEY(2,"SECKILL:ORDER:");

    private int value;
    private String msg;

    RedisConstants(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static RedisConstants valueOf(int statusCode) {
        RedisConstants status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    @Nullable
    public static RedisConstants resolve(int statusCode) {
        for (RedisConstants status : values()) {
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
