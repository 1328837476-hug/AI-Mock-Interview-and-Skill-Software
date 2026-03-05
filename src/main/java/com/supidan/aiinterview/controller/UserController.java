package com.supidan.aiinterview.controller;


import com.supidan.aiinterview.comon.BaseResponse;
import com.supidan.aiinterview.comon.ResultUtils;
import com.supidan.aiinterview.config.CustomUserDetails;
import com.supidan.aiinterview.dto.request.UpdateProfileRequest;
import com.supidan.aiinterview.dto.response.UserInfoResponse;
import com.supidan.aiinterview.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/profile")
    public BaseResponse<UserInfoResponse> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResultUtils.success(
            userService.getProfile(userDetails.getId()));
    }

    /**
     * 更新个人信息
     */
    @PutMapping("/profile")
    public BaseResponse<Void> updateProfile(
            @RequestBody @Valid UpdateProfileRequest req,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateProfile(req, userDetails.getId());
        return ResultUtils.success();
    }
}