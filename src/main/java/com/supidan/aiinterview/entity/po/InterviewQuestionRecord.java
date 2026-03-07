package com.supidan.aiinterview.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("interview_question_record")
public class InterviewQuestionRecord {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sessionId;
    private Long questionId;
    private Boolean isFollowup;
}