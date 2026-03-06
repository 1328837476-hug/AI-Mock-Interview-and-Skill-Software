package com.supidan.aiinterview.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 题目主表
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属岗位
     */
    private String positionCode;

    /**
     * TECHNICAL/SCENARIO/BEHAVIORAL/PROJECT
     */
    private String category;

    /**
     * 主知识点
     */
    private String knowledgeTag;

    /**
     * 细分知识点
     */
    private String subTag;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目完整内容
     */
    private String content;

    /**
     * EASY/MEDIUM/HARD
     */
    private String difficulty;

    /**
     * 
     */
    private Integer estimatedMinutes;

    /**
     * 
     */
    private Integer useCount;

    /**
     * 候选人平均得分
     */
    private BigDecimal avgScore;

    /**
     * 
     */
    private String source;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

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
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPositionCode() == null ? other.getPositionCode() == null : this.getPositionCode().equals(other.getPositionCode()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getKnowledgeTag() == null ? other.getKnowledgeTag() == null : this.getKnowledgeTag().equals(other.getKnowledgeTag()))
            && (this.getSubTag() == null ? other.getSubTag() == null : this.getSubTag().equals(other.getSubTag()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getDifficulty() == null ? other.getDifficulty() == null : this.getDifficulty().equals(other.getDifficulty()))
            && (this.getEstimatedMinutes() == null ? other.getEstimatedMinutes() == null : this.getEstimatedMinutes().equals(other.getEstimatedMinutes()))
            && (this.getUseCount() == null ? other.getUseCount() == null : this.getUseCount().equals(other.getUseCount()))
            && (this.getAvgScore() == null ? other.getAvgScore() == null : this.getAvgScore().equals(other.getAvgScore()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPositionCode() == null) ? 0 : getPositionCode().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getKnowledgeTag() == null) ? 0 : getKnowledgeTag().hashCode());
        result = prime * result + ((getSubTag() == null) ? 0 : getSubTag().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getDifficulty() == null) ? 0 : getDifficulty().hashCode());
        result = prime * result + ((getEstimatedMinutes() == null) ? 0 : getEstimatedMinutes().hashCode());
        result = prime * result + ((getUseCount() == null) ? 0 : getUseCount().hashCode());
        result = prime * result + ((getAvgScore() == null) ? 0 : getAvgScore().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", positionCode=").append(positionCode);
        sb.append(", category=").append(category);
        sb.append(", knowledgeTag=").append(knowledgeTag);
        sb.append(", subTag=").append(subTag);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", estimatedMinutes=").append(estimatedMinutes);
        sb.append(", useCount=").append(useCount);
        sb.append(", avgScore=").append(avgScore);
        sb.append(", source=").append(source);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}