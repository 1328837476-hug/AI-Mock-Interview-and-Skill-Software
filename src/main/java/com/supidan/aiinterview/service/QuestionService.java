package com.supidan.aiinterview.service;

import com.supidan.aiinterview.domain.po.Question;
import java.util.List;

public interface QuestionService {
    /**
     * 按岗位抽题（含历史去重）
     * 技术题3道 + 场景题1道 + 行为题1道
     */
    List<Question> selectQuestions(String positionCode, Long userId);
}