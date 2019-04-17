package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminAuthService;
import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
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
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO, String roleName) throws AccessDeniedException {
        canOperateRole(id, roleName);
        User user = new User();
        String newPassword = bCryptPasswordEncoder.encode(updatePasswordParamDTO.getNewPassword());
        user.setPassword(newPassword);
        user.setGmtModified(new Date());
        return userMapper.updateByPrimaryKey(user) == 1;
    }

    /**
     * 判断当前用户是否有权限操作指定用户组
     *
     * @param id       当前用户id
     * @param roleName 要操作的用户组
     */
    private void canOperateRole(Long id, String roleName) {
        User user = userMapper.selectByPrimaryKey(id);
        Role role = getRoleByUsername(user.getUsername());
        if (!role.getName().equals(roleName)) {
            throw new AccessDeniedException(null);
        }
    }

    private Role getRoleByUsername(String username) {
        return getRole(username, userMapper, userRoleRelationMapper, roleMapper);
    }

    static Role getRole(String username, UserMapper userMapper, UserRoleRelationMapper userRoleRelationMapper, RoleMapper roleMapper) {
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
