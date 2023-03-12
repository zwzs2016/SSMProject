package com.uwan.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RecommenderDTO {
    @NotEmpty
    private Integer userId;

    @NotEmpty
    private Integer size;
}
