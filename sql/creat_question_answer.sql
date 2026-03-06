-- 参考答案表
CREATE TABLE question_answer (
                                 id           BIGINT  NOT NULL AUTO_INCREMENT,
                                 question_id  BIGINT  NOT NULL,
                                 answer_level VARCHAR(20) NOT NULL COMMENT 'BASIC/GOOD/EXCELLENT',
                                 content      TEXT    NOT NULL,
                                 key_points   JSON    COMMENT '答案要点数组',
                                 PRIMARY KEY (id),
                                 KEY idx_question_id (question_id)
) COMMENT '参考答案表';