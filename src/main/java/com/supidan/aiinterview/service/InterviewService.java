package com.supidan.aiinterview.service;

import com.supidan.aiinterview.entity.dto.request.StartInterviewRequest;
import com.supidan.aiinterview.entity.dto.response.StartInterviewResponse;

public interface InterviewService {
    StartInterviewResponse startInterview(
        StartInterviewRequest req, Long userId);
    void endInterview(Long sessionId, Long userId);
}