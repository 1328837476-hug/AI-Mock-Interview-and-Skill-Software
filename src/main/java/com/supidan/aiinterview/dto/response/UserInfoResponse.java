package com.supidan.aiinterview.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String targetPosition;
}