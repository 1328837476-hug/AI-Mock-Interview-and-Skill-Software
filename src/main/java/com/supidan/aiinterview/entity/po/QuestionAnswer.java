package com.supidan.aiinterview.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 参考答案表
 * @TableName question_answer
 */
@TableName(value ="question_answer")
@Data
public class QuestionAnswer implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long questionId;

    /**
     * BASIC/GOOD/EXCELLENT
     */
    private String answerLevel;

    /**
     * 
     */
    private String content;

    /**
     * 答案要点数组
     */
    private Object keyPoints;

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
        QuestionAnswer other = (QuestionAnswer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getAnswerLevel() == null ? other.getAnswerLevel() == null : this.getAnswerLevel().equals(other.getAnswerLevel()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getKeyPoints() == null ? other.getKeyPoints() == null : this.getKeyPoints().equals(other.getKeyPoints()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getAnswerLevel() == null) ? 0 : getAnswerLevel().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getKeyPoints() == null) ? 0 : getKeyPoints().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", questionId=").append(questionId);
        sb.append(", answerLevel=").append(answerLevel);
        sb.append(", content=").append(content);
        sb.append(", keyPoints=").append(keyPoints);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}