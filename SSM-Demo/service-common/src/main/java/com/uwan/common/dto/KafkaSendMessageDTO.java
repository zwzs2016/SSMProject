package com.uwan.common.dto;

import lombok.Data;
//import javax.validation.constraints.NotEmpty;

@Data
public class KafkaSendMessageDTO {
//    @NotEmpty(message = "topic不能为空")
    private String topic;

//    @NotEmpty(message = "发送message不能为空")
    private String message;
}
