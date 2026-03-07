package com.supidan.aiinterview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supidan.aiinterview.entity.po.InterviewSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InterviewSessionMapper
        extends BaseMapper<InterviewSession> {

    @Select("SELECT * FROM interview_session " +
            "WHERE user_id = #{userId} " +
            "AND status = 'ONGOING' " +
            "AND is_deleted = 0 " +
            "LIMIT 1")
    InterviewSession selectOngoingByUserId(
            @Param("userId") Long userId);
}