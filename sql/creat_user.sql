-- 用户表
CREATE TABLE user (
                      id              BIGINT       NOT NULL AUTO_INCREMENT,
                      username        VARCHAR(50)  NOT NULL COMMENT '用户名',
                      password        VARCHAR(100) NOT NULL COMMENT '密码(BCrypt)',
                      nickname        VARCHAR(50)  COMMENT '昵称',
                      avatar_url      VARCHAR(255) COMMENT '头像地址',
                      target_position VARCHAR(30)  COMMENT '目标岗位code',
                      role            TINYINT      NOT NULL DEFAULT 0 COMMENT '0普通用户 1管理员',
                      status          TINYINT      NOT NULL DEFAULT 1 COMMENT '1正常 0禁用',
                      is_deleted      TINYINT(1)   NOT NULL DEFAULT 0,
                      create_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      PRIMARY KEY (id),
                      UNIQUE KEY uk_username (username)
) COMMENT '用户表';