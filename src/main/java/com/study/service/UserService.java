package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.role.Role;
import com.study.pojo.user.User;

import java.util.List;

public interface UserService {

    PageResult<User> findPage(QueryVo vo);

    void saveUserRoleRel(List<User> users, List<Role> roles);

    void saveUserRoleRel(User user, List<Role> roles);

    void saveUserRoleRel(List<User> users, Role role);

    void saveUserRoleRel(User user, Role role);

    void saveUserRoleRel(Long uid, Long rid);

    void fillRoles(List<User> users);

    void fillRoles(User user);

    void deleteUserRoleRel(User user);

    void deleteUserRoleRel(Long uid);

    User findByUsername(String username);

    User findById(Long userId);

    void fillPublishCount(User user);

    void fillFansCount(User user);

    void fillReviewCount(User user);

    Long findFansCountByUid(Long uid);

    void fillFollowCount(User user);

    Long findFollowCountByUid(Long id);

    List<User> findFansByUid(Long userId);

    List<Long> findFansIdsByStartId(Long userId);

    List<User> findStartByUid(Long userId);

    List<Long> findStartIdsByFansId(Long userId);

    void save(User user);

    void update(User user);

    void assignmentNormalRole(User user);

    void saveStartFansRel(Long uid, Long id);

    List<Long> findFollowIdsBUid(Long id);

    Boolean findStartIByFansId(Long id, Long id1);

    void deleteStartFansRel(Long uid, Long id);
}
