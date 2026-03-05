package com.supidan.aiinterview.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String  token;
    private String  username;
    private Integer role;
}