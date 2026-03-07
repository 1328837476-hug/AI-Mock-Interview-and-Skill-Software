package com.supidan.aiinterview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supidan.aiinterview.entity.po.InterviewQuestionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface InterviewQuestionRecordMapper
        extends BaseMapper<InterviewQuestionRecord> {

    /**
     * 查询用户近5次面试用过的题目ID（历史去重用）
     */
    @Select("SELECT DISTINCT iqr.question_id " +
            "FROM interview_question_record iqr " +
            "INNER JOIN interview_session s ON iqr.session_id = s.id " +
            "WHERE s.user_id = #{userId} " +
            "AND s.id IN (" +
            "  SELECT id FROM interview_session " +
            "  WHERE user_id = #{userId} " +
            "  ORDER BY create_time DESC LIMIT 5" +
            ")")
    List<Long> selectRecentUsedQuestionIds(
            @Param("userId") Long userId);

    /**
     * 查询本次面试用到的所有题目ID（更新use_count用）
     */
    @Select("SELECT question_id FROM interview_question_record " +
            "WHERE session_id = #{sessionId} " +
            "AND is_followup = 0")
    List<Long> selectQuestionIdsBySessionId(   // ← 这个方法必须有
                                               @Param("sessionId") Long sessionId);
}