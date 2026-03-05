package com.supidan.aiinterview.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartInterviewResponse {
    private String sessionId;
    private String interviewMode;
    private String positionCode;
    private String wsUrl;
}