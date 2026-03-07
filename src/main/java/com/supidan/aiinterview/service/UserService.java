package com.supidan.aiinterview.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supidan.aiinterview.entity.po.User;
import com.supidan.aiinterview.entity.dto.request.LoginRequest;
import com.supidan.aiinterview.entity.dto.request.RegisterRequest;
import com.supidan.aiinterview.entity.dto.request.UpdateProfileRequest;
import com.supidan.aiinterview.entity.dto.response.TokenResponse;
import com.supidan.aiinterview.entity.dto.response.UserInfoResponse;

/**
* @author fink
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2026-03-05 10:43:22
*/
public interface UserService extends IService<User> {

        /**
         * 用户注册
         *
         * @param req
         */
        void            register(RegisterRequest req);

        /**
         * 用户登录
         *
         * @param req
         * @return
         */
        TokenResponse   login(LoginRequest req);

        /**
         * 获取用户信息
         *
         * @param userId
         * @return
         */
        UserInfoResponse getProfile(Long userId);

        /**
         * 更新用户信息
         *
         * @param req
         * @param userId
         */
        void updateProfile(UpdateProfileRequest req, Long userId);

}
