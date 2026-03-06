package com.supidan.aiinterview.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StartInterviewRequest {

    @NotBlank(message = "岗位不能为空")
    @Pattern(regexp = "java_backend|web_frontend",
            message = "岗位代码非法")
    private String positionCode;

    private Boolean audioEnabled = false;
}