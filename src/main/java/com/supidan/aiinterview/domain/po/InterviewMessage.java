package com.supidan.aiinterview.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 对话记录表
 * @TableName interview_message
 */
@TableName(value ="interview_message")
@Data
public class InterviewMessage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long sessionId;

    /**
     * user/assistant
     */
    private String role;

    /**
     * 
     */
    private String content;

    /**
     * 语音文件路径（阶段三使用）
     */
    private String audioUrl;

    /**
     * TEXT/AUDIO
     */
    private String inputType;

    /**
     * 语音识别置信度（阶段三使用）
     */
    private BigDecimal asrConfidence;

    /**
     * 情感分析结果（阶段三使用）
     */
    private Object emotionData;

    /**
     * 消息序号
     */
    private Integer sequence;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        InterviewMessage other = (InterviewMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSessionId() == null ? other.getSessionId() == null : this.getSessionId().equals(other.getSessionId()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAudioUrl() == null ? other.getAudioUrl() == null : this.getAudioUrl().equals(other.getAudioUrl()))
            && (this.getInputType() == null ? other.getInputType() == null : this.getInputType().equals(other.getInputType()))
            && (this.getAsrConfidence() == null ? other.getAsrConfidence() == null : this.getAsrConfidence().equals(other.getAsrConfidence()))
            && (this.getEmotionData() == null ? other.getEmotionData() == null : this.getEmotionData().equals(other.getEmotionData()))
            && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSessionId() == null) ? 0 : getSessionId().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAudioUrl() == null) ? 0 : getAudioUrl().hashCode());
        result = prime * result + ((getInputType() == null) ? 0 : getInputType().hashCode());
        result = prime * result + ((getAsrConfidence() == null) ? 0 : getAsrConfidence().hashCode());
        result = prime * result + ((getEmotionData() == null) ? 0 : getEmotionData().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", role=").append(role);
        sb.append(", content=").append(content);
        sb.append(", audioUrl=").append(audioUrl);
        sb.append(", inputType=").append(inputType);
        sb.append(", asrConfidence=").append(asrConfidence);
        sb.append(", emotionData=").append(emotionData);
        sb.append(", sequence=").append(sequence);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}