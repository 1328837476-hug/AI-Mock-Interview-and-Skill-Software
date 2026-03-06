package com.supidan.aiinterview.controller;


import com.supidan.aiinterview.comon.BaseResponse;
import com.supidan.aiinterview.comon.ResultUtils;
import com.supidan.aiinterview.config.CustomUserDetails;
import com.supidan.aiinterview.dto.request.StartInterviewRequest;
import com.supidan.aiinterview.dto.response.StartInterviewResponse;
import com.supidan.aiinterview.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "面试模块", description = "开始面试、结束面试")
@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @Operation(summary = "开始面试")
    @PostMapping("/start")
    public BaseResponse<StartInterviewResponse> start(
            @RequestBody @Valid StartInterviewRequest req,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResultUtils.success(
                interviewService.startInterview(
                        req, userDetails.getId()));
    }

    @Operation(summary = "结束面试")
    @PostMapping("/{id}/end")
    public BaseResponse<Void> end(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        interviewService.endInterview(id, userDetails.getId());
        return ResultUtils.success();
    }
}