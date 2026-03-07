package com.supidan.aiinterview.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("interview_report")
public class InterviewReport {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sessionId;
    private Long userId;
    private String positionCode;
    private String generateStatus;  // PENDING / SUCCESS / FAILED
    private BigDecimal technicalScore;
    private BigDecimal depthScore;
    private BigDecimal logicScore;
    private BigDecimal matchingScore;
    private BigDecimal totalScore;
    private String strengths;       // JSON数组字符串
    private String weaknesses;      // JSON数组字符串
    private String suggestions;     // JSON数组字符串

    @TableLogic
    private Integer isDeleted;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}