package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminAuthService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;

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
        new FuncUtils().canOperateRole(id, roleName);
        User user = new User();
        String newPassword = bCryptPasswordEncoder.encode(updatePasswordParamDTO.getNewPassword());
        user.setPassword(newPassword);
        user.setGmtModified(new Date());
        return userMapper.updateByPrimaryKey(user) == 1;
    }
}
