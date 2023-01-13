package com.bamboo.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicInfoDTO {
//    @NotBlank(message = "标题不能为空")
//    @Length(min = 5,max = 20)
    private String title;

    private String roomId;

//    @Length(max = 100)
    private String remark;

    private String author;
}
