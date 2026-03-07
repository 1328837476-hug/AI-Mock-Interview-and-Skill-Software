package com.supidan.aiinterview.service.impl;

import com.supidan.aiinterview.entity.po.Question;
import com.supidan.aiinterview.mapper.InterviewQuestionRecordMapper;
import com.supidan.aiinterview.mapper.QuestionMapper;
import com.supidan.aiinterview.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper                questionMapper;
    private final InterviewQuestionRecordMapper recordMapper;

    @Override
    public List<Question> selectQuestions(
            String positionCode, Long userId) {

        // 1. 查询近5次用过的题目ID（历史去重）
        List<Long> excludeIds = recordMapper
            .selectRecentUsedQuestionIds(userId);
        if (excludeIds == null || excludeIds.isEmpty()) {
            excludeIds = List.of(-1L); // 占位防SQL语法错误
        }

        // 2. 按题型分配比例抽题
        List<Question> result = new ArrayList<>();

        // 技术题3道
        result.addAll(doSelect(
            positionCode, "TECHNICAL", 3, excludeIds));
        // 场景题1道
        result.addAll(doSelect(
            positionCode, "SCENARIO", 1, excludeIds));
        // 行为题1道
        result.addAll(doSelect(
            positionCode, "BEHAVIORAL", 1, excludeIds));

        log.info("抽题完成 positionCode={} 共{}道",
            positionCode, result.size());
        return result;
    }

    private List<Question> doSelect(
            String positionCode, String category,
            int limit, List<Long> excludeIds) {

        List<Question> list = questionMapper.selectRandomExclude(
            positionCode, category, excludeIds, limit);

        // 如果排除后数量不足，补充不排除版本
        if (list.size() < limit) {
            list = questionMapper.selectRandom(
                positionCode, category, limit);
        }
        return list;
    }
}