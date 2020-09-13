package com.study.dao.role;

import com.study.pojo.role.Role;
import com.study.pojo.role.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleDao {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    void saveRolePermissionRel(@Param("rid") Long rid,@Param("pid") Long pid);

    void deleteRolePermissionRel(Long id);

    List<Long> findRidsByUid(Long id);

    List<Long> findRidsByPid(Long id);
}