-- 面试会话表
CREATE TABLE interview_session (
                                   id              BIGINT      NOT NULL AUTO_INCREMENT,
                                   user_id         BIGINT      NOT NULL,
                                   position_code   VARCHAR(30) NOT NULL,
                                   status          VARCHAR(20) NOT NULL DEFAULT 'PREPARING'
                                       COMMENT 'PREPARING/ONGOING/FINISHED/EVALUATED',
                                   stage           VARCHAR(20) NOT NULL DEFAULT 'OPENING'
                                       COMMENT 'OPENING/TECHNICAL/SCENARIO/BEHAVIORAL/CLOSING',
                                   interview_mode  VARCHAR(20) NOT NULL DEFAULT 'GENERAL'
                                       COMMENT 'PERSONALIZED/GENERAL',
                                   audio_enabled   TINYINT(1)  NOT NULL DEFAULT 0,
                                   total_turns     INT         NOT NULL DEFAULT 0,
                                   duration_seconds INT        COMMENT '面试时长(秒)',
                                   question_ids    JSON        COMMENT '本次抽到的题目ID列表',
                                   start_time      DATETIME,
                                   end_time        DATETIME,
                                   is_deleted      TINYINT(1)  NOT NULL DEFAULT 0,
                                   create_time     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   update_time     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (id),
                                   KEY idx_user_id (user_id),
                                   KEY idx_status (status)
) COMMENT '面试会话表';