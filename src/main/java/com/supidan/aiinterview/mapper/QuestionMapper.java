package com.supidan.aiinterview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supidan.aiinterview.domain.po.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 按岗位+题型随机抽题（排除历史题目）
     */
    @Select("<script>" +
            "SELECT * FROM question " +
            "WHERE position_code = #{positionCode} " +
            "AND category = #{category} " +
            "AND status = 1 AND is_deleted = 0 " +
            "AND id NOT IN " +
            "<foreach item='id' collection='excludeIds' " +
            "open='(' separator=',' close=')'>#{id}</foreach> " +
            "ORDER BY RAND() LIMIT #{limit}" +
            "</script>")
    List<Question> selectRandomExclude(
            @Param("positionCode") String positionCode,
            @Param("category") String category,
            @Param("excludeIds") List<Long> excludeIds,
            @Param("limit") int limit);

    /**
     * 按岗位+题型随机抽题（不排除）
     */
    @Select("SELECT * FROM question " +
            "WHERE position_code = #{positionCode} " +
            "AND category = #{category} " +
            "AND status = 1 AND is_deleted = 0 " +
            "ORDER BY RAND() LIMIT #{limit}")
    List<Question> selectRandom(
            @Param("positionCode") String positionCode,
            @Param("category") String category,
            @Param("limit") int limit);
}