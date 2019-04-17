package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.dto.UserUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/03/21
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
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
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public UserResultDTO updateUser(Long id, UserUpdateParamDTO userUpdateParamDTO, String roleName) throws AccessDeniedException, InvocationTargetException, IllegalAccessException {
        new FuncUtils().canOperateRole(id, roleName);
        User user = new User();
        org.apache.commons.beanutils.BeanUtils.copyProperties(user, userUpdateParamDTO);
        user.setGmtModified(new Date());
        LoggerUtil.logger.error(user.toString());
        userMapper.updateByPrimaryKeySelective(user);
        UserResultDTO userResultDTO = new UserResultDTO();
        BeanUtils.copyProperties(user, userResultDTO);
        return userResultDTO;
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
    public User getUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username);
        List<User> adminList = userMapper.selectByExample(userExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }
}
