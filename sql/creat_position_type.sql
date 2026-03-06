-- 岗位表
CREATE TABLE position_type (
                               id          BIGINT      NOT NULL AUTO_INCREMENT,
                               code        VARCHAR(30) NOT NULL COMMENT '岗位代码',
                               name        VARCHAR(50) NOT NULL COMMENT '岗位名称',
                               description VARCHAR(255) COMMENT '岗位描述',
                               core_skills JSON        COMMENT '核心技能栈',
                               status      TINYINT(1)  NOT NULL DEFAULT 1,
                               sort_order  INT         NOT NULL DEFAULT 0,
                               PRIMARY KEY (id),
                               UNIQUE KEY uk_code (code)
) COMMENT '岗位表';