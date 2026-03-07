package com.supidan.aiinterview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supidan.aiinterview.entity.po.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author fink
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-03-05 10:43:22
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username = #{username} AND is_deleted = 0")
    User selectByUsername(@Param("username") String username);
}




