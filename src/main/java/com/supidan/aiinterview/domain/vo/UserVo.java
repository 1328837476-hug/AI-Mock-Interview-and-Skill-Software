package com.supidan.aiinterview.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.supidan.aiinterview.domain.po.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserVo implements Serializable {

    /**
     *
     */

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(BCrypt)
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 目标岗位code
     */
    private String targetPosition;

    /**
     * 0普通用户 1管理员
     */
    private Integer role;

    /**
     * 1正常 0禁用
     */
    private Integer status;





    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}