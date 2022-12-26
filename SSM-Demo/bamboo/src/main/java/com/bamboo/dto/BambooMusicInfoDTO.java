package com.bamboo.dto;

import com.bamboo.annotation.ImageBase64Size;
import com.bamboo.annotation.SensitiveWord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BambooMusicInfoDTO {
    @NotBlank(message = "标题不能为空")
    @Length(min = 5,max = 20)
    @SensitiveWord
    private String title;

    @ImageBase64Size
    private String imageBase64File;

    @Length(max = 100)
    @SensitiveWord
    private String remark;

    private String username;

    private String liveCode;
}
