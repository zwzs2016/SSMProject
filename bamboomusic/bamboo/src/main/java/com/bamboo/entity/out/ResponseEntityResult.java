package com.bamboo.entity.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityResult<T> implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 返回结果状态
     */
    private String code;

    /**
     * 结果信息描述
     */
    private String message;

    /**
     * 返回结果数据
     */
    private T data;

    private Boolean success;

}
