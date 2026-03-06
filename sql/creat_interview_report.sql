CREATE TABLE interview_report (
                                  id               BIGINT      NOT NULL AUTO_INCREMENT,
                                  session_id       BIGINT      NOT NULL UNIQUE,
                                  user_id          BIGINT      NOT NULL,
                                  position_code    VARCHAR(30) NOT NULL,
                                  generate_status  VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                  technical_score  DECIMAL(5,2),
                                  depth_score      DECIMAL(5,2),
                                  logic_score      DECIMAL(5,2),
                                  matching_score   DECIMAL(5,2),
                                  total_score      DECIMAL(5,2),
                                  strengths        JSON,
                                  weaknesses       JSON,
                                  suggestions      JSON,
                                  is_deleted       TINYINT(1)  NOT NULL DEFAULT 0,
                                  create_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  update_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (id),
                                  KEY idx_session_id (session_id),
                                  KEY idx_user_id (user_id)
) COMMENT '面试评估报告表';