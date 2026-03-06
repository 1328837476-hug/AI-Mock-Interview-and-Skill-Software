package com.supidan.aiinterview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supidan.aiinterview.domain.po.InterviewMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface InterviewMessageMapper
        extends BaseMapper<InterviewMessage> {

    @Select("SELECT * FROM interview_message " +
            "WHERE session_id = #{sessionId} " +
            "ORDER BY sequence ASC")
    List<InterviewMessage> selectBySessionId(
            @Param("sessionId") Long sessionId);
}