package com.bamboo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPreferencesDTO {
    @NotEmpty(message = "liveCode不能为空")
    private String liveCode;

    @NotEmpty(message = "preference不能为空")
    private Integer preference;
}
