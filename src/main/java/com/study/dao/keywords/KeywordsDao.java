package com.study.dao.keywords;

import com.study.pojo.keywords.Keywords;
import com.study.pojo.keywords.KeywordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KeywordsDao {
    long countByExample(KeywordsExample example);

    int deleteByExample(KeywordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Keywords record);

    int insertSelective(Keywords record);

    List<Keywords> selectByExample(KeywordsExample example);

    Keywords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Keywords record, @Param("example") KeywordsExample example);

    int updateByExample(@Param("record") Keywords record, @Param("example") KeywordsExample example);

    int updateByPrimaryKeySelective(Keywords record);

    int updateByPrimaryKey(Keywords record);
}