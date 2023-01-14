package com.uwan.common.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class KafkaSendMessageDTO {
    @NotEmpty(message = "topic不能为空")
    private String topic;

    @NotNull(message = "操作方式不能为空")//eg add,query...
    private Integer operate;

    @NotEmpty(message = "发送message不能为空")
    private String message;
}
