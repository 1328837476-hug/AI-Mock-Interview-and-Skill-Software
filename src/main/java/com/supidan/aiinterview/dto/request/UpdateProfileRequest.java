package com.supidan.aiinterview.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;

    @Size(max = 255, message = "头像地址过长")
    private String avatarUrl;

    private String targetPosition; // java_backend / web_frontend
}