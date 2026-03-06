-- 对话记录表
CREATE TABLE interview_message (
                                   id               BIGINT      NOT NULL AUTO_INCREMENT,
                                   session_id       BIGINT      NOT NULL,
                                   role             VARCHAR(10) NOT NULL COMMENT 'user/assistant',
                                   content          TEXT        NOT NULL,
                                   audio_url        VARCHAR(255) COMMENT '语音文件路径（阶段三使用）',
                                   input_type       VARCHAR(10) DEFAULT 'TEXT' COMMENT 'TEXT/AUDIO',
                                   asr_confidence   DECIMAL(4,3) COMMENT '语音识别置信度（阶段三使用）',
                                   emotion_data     JSON        COMMENT '情感分析结果（阶段三使用）',
                                   sequence         INT         NOT NULL COMMENT '消息序号',
                                   create_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (id),
                                   KEY idx_session_id (session_id),
                                   KEY idx_session_seq (session_id, sequence)
) COMMENT '对话记录表';