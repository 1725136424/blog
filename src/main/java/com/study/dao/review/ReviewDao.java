package com.study.dao.review;

import com.study.pojo.review.Review;
import com.study.pojo.review.ReviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReviewDao {
    long countByExample(ReviewExample example);

    int deleteByExample(ReviewExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Review record, @Param("example") ReviewExample example);

    int updateByExample(@Param("record") Review record, @Param("example") ReviewExample example);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}