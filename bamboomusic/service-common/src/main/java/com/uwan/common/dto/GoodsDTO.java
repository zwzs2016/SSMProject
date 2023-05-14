package com.uwan.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal itemPrice;

    @Length(max = 225)
    private String extraInfo;

    private int stock;

    private byte[] itemImg;
}
