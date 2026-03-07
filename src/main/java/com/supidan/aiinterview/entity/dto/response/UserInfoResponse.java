package com.supidan.aiinterview.entity.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String targetPosition;
}