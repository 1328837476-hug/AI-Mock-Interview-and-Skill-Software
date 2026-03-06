package com.supidan.aiinterview.service;

import com.supidan.aiinterview.dto.request.StartInterviewRequest;
import com.supidan.aiinterview.dto.response.StartInterviewResponse;

public interface InterviewService {
    StartInterviewResponse startInterview(
        StartInterviewRequest req, Long userId);
    void endInterview(Long sessionId, Long userId);
}