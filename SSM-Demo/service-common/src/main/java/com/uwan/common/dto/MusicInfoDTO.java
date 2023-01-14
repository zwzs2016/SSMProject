package com.uwan.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class MusicInfoDTO {
    @NotEmpty(message = "id不能为空")
    private String id;

    @NotEmpty(message = "标题不能为空")
    @Length(min = 5,max = 20)
    private String title;

    private String roomId;

    @Length(max = 100)
    private String remark;

    private String author;

    private String liveUrl;

    private byte[] imgFile;

}
