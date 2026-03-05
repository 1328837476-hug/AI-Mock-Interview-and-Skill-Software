package com.supidan.aiinterview.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "用户名须为4-20位字母或数字")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码须为6-20位")
    private String password;

    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;
}