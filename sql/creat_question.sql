-- 题目主表
CREATE TABLE question (
                          id                BIGINT       NOT NULL AUTO_INCREMENT,
                          position_code     VARCHAR(30)  NOT NULL COMMENT '所属岗位',
                          category          VARCHAR(20)  NOT NULL COMMENT 'TECHNICAL/SCENARIO/BEHAVIORAL/PROJECT',
                          knowledge_tag     VARCHAR(50)  NOT NULL COMMENT '主知识点',
                          sub_tag           VARCHAR(50)  COMMENT '细分知识点',
                          title             VARCHAR(200) NOT NULL COMMENT '题目标题',
                          content           TEXT         NOT NULL COMMENT '题目完整内容',
                          difficulty        VARCHAR(10)  NOT NULL DEFAULT 'MEDIUM' COMMENT 'EASY/MEDIUM/HARD',
                          estimated_minutes INT          DEFAULT 3,
                          use_count         INT          NOT NULL DEFAULT 0,
                          avg_score         DECIMAL(5,2) COMMENT '候选人平均得分',
                          source            VARCHAR(20)  NOT NULL DEFAULT 'MANUAL',
                          status            TINYINT(1)   NOT NULL DEFAULT 1,
                          is_deleted        TINYINT(1)   NOT NULL DEFAULT 0,
                          create_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          update_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (id),
                          KEY idx_position_category (position_code, category),
                          KEY idx_knowledge_tag (knowledge_tag)
) COMMENT '题目主表';