package com.supidan.aiinterview.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 面试会话实体
 */
@Data
@TableName("interview_session")
public class InterviewSession {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 岗位代码 */
    private String positionCode;

    /** 会话状态 PREPARING/ONGOING/FINISHED/EVALUATED */
    private String status;

    /** 当前阶段 OPENING/TECHNICAL/SCENARIO/BEHAVIORAL/CLOSING */
    private String stage;

    /** 面试模式 PERSONALIZED/GENERAL */
    private String interviewMode;

    /** 是否开启语音 */
    private Boolean audioEnabled;

    /** 总对话轮数 */
    private Integer totalTurns;

    /** 面试时长（秒） */
    private Integer durationSeconds;

    /** 本次抽到的题目ID列表 JSON */
    private String questionIds;

    /** 面试开始时间 */
    private LocalDateTime startTime;

    /** 面试结束时间 */
    private LocalDateTime endTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer isDeleted;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
