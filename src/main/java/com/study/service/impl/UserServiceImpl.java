package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.role.RoleDao;
import com.study.dao.user.UserDao;
import com.study.pojo.article.Article;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.review.Review;
import com.study.pojo.role.Role;
import com.study.pojo.role.RoleExample;
import com.study.pojo.user.User;
import com.study.pojo.user.UserExample;
import com.study.service.ArticleService;
import com.study.service.ReviewService;
import com.study.service.RoleService;
import com.study.service.UserService;
import com.study.util.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public PageResult<User> findPage(QueryVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        UserExample userExample =  new UserExample();
        userExample.setOrderByClause("register_date asc");
        if (vo.getKeywords() != null) {
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUsernameLike("%" + vo.getKeywords() + "%");
        }
        List<User> users = userDao.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new PageResult<>(pageInfo.getTotal(), users);
    }

    @Override
    public void saveUserRoleRel(List<User> users, List<Role> roles) {
        for (User user : users) {
            saveUserRoleRel(user, roles);
        }
    }

    @Override
    public void saveUserRoleRel(User user, List<Role> roles) {
        for (Role role : roles) {
            saveUserRoleRel(user, role);
        }
    }

    @Override
    public void saveUserRoleRel(List<User> users, Role role) {
        for (User user : users) {
            saveUserRoleRel(user, role);
        }
    }

    @Override
    public void saveUserRoleRel(User user, Role role) {
        if (user != null && role != null) {
            saveUserRoleRel(user.getId(), role.getId());
        }
    }

    @Override
    public void saveUserRoleRel(Long uid, Long rid) {
        userDao.saveUserRoleRel(uid, rid);
    }

    @Override
    public void fillRoles(List<User> users) {
        for (User user : users) {
            fillRoles(user);
        }
    }

    @Override
    public void fillRoles(User user) {
        // 获取所有的角色id
        List<Long> rids = roleService.findRidsByUid(user.getId());
        // 获取所有角色
        List<Role> roles = roleService.findByRids(rids);
        // 添加角色
        user.setRoles(roles);
    }

    @Override
    public void deleteUserRoleRel(User user) {
        deleteUserRoleRel(user.getId());
    }

    @Override
    public void deleteUserRoleRel(Long uid) {
        userDao.deleteUserRoleRel(uid);
    }

    @Override
    public User findByUsername(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userDao.selectByExample(userExample);
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User findById(Long userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public void fillPublishCount(User user) {
        // 获取当前用户下的所有文章数量
        user.setPublishCount(articleService.findPublishCountByUid(user.getId()));
    }

    @Override
    public void fillFansCount(User user) {
        // 获取当前用户下的粉丝数量
        user.setFansCount(findFansCountByUid(user.getId()));
    }


    @Override
    public void fillReviewCount(User user) {
        // 获取当前的评论数量
        user.setReviewCount(reviewService.findCountReview(user.getId(), true));
    }


    @Override
    public Long findFansCountByUid(Long uid) {
        return userDao.selectFansCountByUid(uid);
    }

    @Override
    public void fillFollowCount(User user) {
        // 填充关注数量
        user.setFollowCount(findFollowCountByUid(user.getId()));
    }

    @Override
    public Long findFollowCountByUid(Long id) {
        return userDao.selectFollowCountByUid(id);
    }

    @Override
    public List<User> findFansByUid(Long userId) {
        List<Long> fansIds =  findFansIdsByStartId(userId);
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("register_date desc");
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdIn(fansIds);
        return userDao.selectByExample(userExample);
    }

    @Override
    public List<Long> findFansIdsByStartId(Long userId) {
        return userDao.selectFansIdsByStartId(userId);
    }

    @Override
    public List<User> findStartByUid(Long userId) {
        List<Long> startIds = findStartIdsByFansId(userId);
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("register_date desc");
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdIn(startIds);
        return userDao.selectByExample(userExample);
    }

    @Override
    public List<Long> findStartIdsByFansId(Long userId) {
        return userDao.selectStartIdsByFansId(userId);
    }

    @Override
    public void save(User user) {
        userDao.insertSelective(user);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void assignmentNormalRole(User user) {
        // 分配普通用户角色
        Role role = roleService.findById(Field.normalRole);
        userDao.saveUserRoleRel(user.getId(), role.getId());
    }

    @Override
    public void saveStartFansRel(Long uid, Long id) {
        userDao.insertStartFansRel(uid, id);
    }

    @Override
    public List<Long> findFollowIdsBUid(Long id) {
        return userDao.selectFollowIdsBUid(id);
    }

    @Override
    public Boolean findStartIByFansId(Long id, Long id1) {
        Long count = userDao.selectStartIByFansId(id, id1);
        return count > 0;
    }

    @Override
    public void deleteStartFansRel(Long uid, Long id) {
        userDao.deleteStartFansRel(uid, id);
    }
}
