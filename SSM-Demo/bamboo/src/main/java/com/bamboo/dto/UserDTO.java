package com.bamboo.dto;

import com.bamboo.annotation.Identical;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Identical
public class UserDTO {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5,max = 20)
    private String name;

    private String sex;

    private String age;

    private String height;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 20)
    private String password;

    private String repassword;
}
