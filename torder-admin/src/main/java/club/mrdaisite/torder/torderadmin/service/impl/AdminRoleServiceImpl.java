package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public Role getRoleByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        Long userId = userList.get(0).getId();
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.or().andUserIdEqualTo(userId);
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.selectByExample(userRoleRelationExample);
        UserRoleRelation userRoleRelation = userRoleRelationList.get(0);
        return roleMapper.selectByPrimaryKey(userRoleRelation.getRoleId());
    }
}
