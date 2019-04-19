package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.dto.UserUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dai
 * @date 2019/03/21
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public List<Object> listUser(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<User> userList = userMapper.selectByExample(new UserExample());
        PageInfo pageInfo = new PageInfo<>(userList);
        List<Object> pageInfoList = pageInfo.getList();
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < pageInfoList.size(); i++) {
            targetList.add(new UserResultDTO());
        }
        return new FuncUtils().beanUtilsCopyListProperties(pageInfoList, targetList);
    }

    @Override
    public List<User> listUserByRoleId(Long roleId) throws CustomException {
        if (!adminRoleService.roleExists(roleId)) {
            throw new CustomException("不存在的角色组");
        }
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.or().andRoleIdEqualTo(roleId);
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.selectByExample(userRoleRelationExample);
        return userRoleRelationList.stream()
                .map(userRoleRelation -> userMapper.selectByPrimaryKey(userRoleRelation.getUserId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public UserResultDTO getUserById(Long id) throws CustomException {
        UserResultDTO userResultDTO = new UserResultDTO();
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new CustomException("不存在的用户");
        }
        BeanUtils.copyProperties(user, userResultDTO);
        return userResultDTO;
    }

    @Override
    public User getUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username);
        List<User> adminList = userMapper.selectByExample(userExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO, String roleName) {
        User user = new User();
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        UserResultDTO userResultDTO = new UserResultDTO();

        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(roleName);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        Role role = roleList.get(0);
        BeanUtils.copyProperties(userInsertParamDTO, user);

        String bCryptPassword = bCryptPasswordEncoder.encode(userInsertParamDTO.getPassword());
        user.setPassword(bCryptPassword);
        user.setScore(0);
        user.setEnabled(true);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insert(user);
        userRoleRelation.setUserId(user.getId());
        userRoleRelation.setRoleId(role.getId());
        userRoleRelation.setGmtCreate(new Date());
        userRoleRelation.setGmtModified(new Date());
        userRoleRelationMapper.insert(userRoleRelation);
        BeanUtils.copyProperties(user, userResultDTO);
        return userResultDTO;
    }

    @Override
    public UserResultDTO updateUser(Long id, UserUpdateParamDTO userUpdateParamDTO, String roleName) throws AccessDeniedException, InvocationTargetException, IllegalAccessException {
        new FuncUtils().canOperateRole(id, roleName);
        User user = new User();
        org.apache.commons.beanutils.BeanUtils.copyProperties(user, userUpdateParamDTO);
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        UserResultDTO userResultDTO = new UserResultDTO();
        BeanUtils.copyProperties(user, userResultDTO);
        return userResultDTO;
    }

    @Override
    public Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO, String roleName) throws AccessDeniedException {
        new FuncUtils().canOperateRole(id, roleName);
        User user = new User();
        String newPassword = bCryptPasswordEncoder.encode(updatePasswordParamDTO.getNewPassword());
        user.setPassword(newPassword);
        user.setGmtModified(new Date());
        return userMapper.updateByPrimaryKey(user) == 1;
    }

    @Override
    public void deleteUser(Long id, String roleName) throws AccessDeniedException {
        new FuncUtils().canOperateRole(id, roleName);
        User user = userMapper.selectByPrimaryKey(id);
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.or().andUserIdEqualTo(user.getId());
        userRoleRelationMapper.deleteByExample(userRoleRelationExample);
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean userExists(Long id) {
        return userMapper.selectByPrimaryKey(id) != null;
    }
}