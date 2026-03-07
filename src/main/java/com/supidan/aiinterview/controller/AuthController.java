package com.supidan.aiinterview.controller;


import com.supidan.aiinterview.comon.BaseResponse;
import com.supidan.aiinterview.comon.ResultUtils;
import com.supidan.aiinterview.entity.dto.request.LoginRequest;
import com.supidan.aiinterview.entity.dto.request.RegisterRequest;
import com.supidan.aiinterview.entity.dto.response.TokenResponse;
import com.supidan.aiinterview.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public BaseResponse<Void> register(
            @RequestBody @Valid RegisterRequest req) {
        userService.register(req);
        return ResultUtils.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseResponse<TokenResponse> login(
            @RequestBody @Valid LoginRequest req) {
        return ResultUtils.success(userService.login(req));
    }
}