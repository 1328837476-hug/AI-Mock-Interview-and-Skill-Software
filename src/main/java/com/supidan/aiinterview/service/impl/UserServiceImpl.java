package com.supidan.aiinterview.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supidan.aiinterview.comon.ErrorCode;
import com.supidan.aiinterview.domain.po.User;
import com.supidan.aiinterview.dto.request.*;
import com.supidan.aiinterview.dto.response.*;
import com.supidan.aiinterview.exception.BizException;
import com.supidan.aiinterview.mapper.UserMapper;
import com.supidan.aiinterview.service.UserService;
import com.supidan.aiinterview.util.JwtUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final UserMapper      userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil         jwtUtil;

    @Override
    public void register(RegisterRequest req) {
        // 1. 检查用户名唯一性
        User existing = userMapper.selectByUsername(req.getUsername());
        if (existing != null) {
            throw new BizException(ErrorCode.DATA_CONFLICT, "用户名已被占用");
        }
        // 2. 构建并保存
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setNickname(req.getNickname() != null
                ? req.getNickname() : req.getUsername());
        user.setRole(0);
        user.setStatus(1);
        userMapper.insert(user);
        log.info("用户注册成功: {}", req.getUsername());
    }

    @Override
    public TokenResponse login(LoginRequest req) {
        User user = userMapper.selectByUsername(req.getUsername());
        if (user == null || !passwordEncoder.matches(
                req.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.PARAM_ERROR, "用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BizException(ErrorCode.FORBIDDEN, "账号已被禁用");
        }
        String token = jwtUtil.generateToken(
                user.getId(), user.getUsername(), user.getRole());
        log.info("用户登录成功: {}", req.getUsername());
        return new TokenResponse(token, user.getUsername(), user.getRole());
    }

    @Override
    public UserInfoResponse getProfile(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        UserInfoResponse resp = new UserInfoResponse();
        resp.setId(String.valueOf(user.getId()));
        resp.setUsername(user.getUsername());
        resp.setNickname(user.getNickname());
        resp.setAvatarUrl(user.getAvatarUrl());
        resp.setTargetPosition(user.getTargetPosition());
        return resp;
    }
    @Override
    public void updateProfile(UpdateProfileRequest req, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        if (req.getNickname() != null) {
            user.setNickname(req.getNickname());
        }
        if (req.getAvatarUrl() != null) {
            user.setAvatarUrl(req.getAvatarUrl());
        }
        if (req.getTargetPosition() != null) {
            user.setTargetPosition(req.getTargetPosition());
        }
        userMapper.updateById(user);
        log.info("用户信息更新成功 userId={}", userId);
    }
}