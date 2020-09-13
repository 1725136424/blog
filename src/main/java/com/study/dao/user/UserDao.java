package com.study.dao.user;

import com.study.pojo.user.User;
import com.study.pojo.user.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    void saveUserRoleRel(@Param("uid") Long uid, @Param("rid") Long rid);

    void deleteUserRoleRel(Long uid);

    Long selectFansCountByUid(Long uid);

    Long selectFollowCountByUid(Long id);

    List<Long> selectFansIdsByStartId(Long userId);

    List<Long> selectStartIdsByFansId(Long userId);

    void insertStartFansRel(@Param("sid") Long uid, @Param("fid") Long id);

    List<Long> selectFollowIdsBUid(Long id);

    Long selectStartIByFansId(@Param("sid") Long id, @Param("fid") Long id1);

    void deleteStartFansRel(@Param("sid")  Long uid, @Param("fid") Long id);
}