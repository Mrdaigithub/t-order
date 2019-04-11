package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.component.WebLogAspect;
import club.mrdaisite.torder.torderadmin.dao.UserRoleRelationDao;
import club.mrdaisite.torder.torderadmin.dto.AdminChangeUserPasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.UserRegisterParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.model.Permission;
import club.mrdaisite.torder.tordermbg.model.User;
import club.mrdaisite.torder.tordermbg.model.UserExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author dai
 */
@Service
public class AdminServiceImpl implements AdminService {
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
    private UserRoleRelationDao userRoleRelationDao;

    @Override
    public User getAdminByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username);
        List<User> adminList = userMapper.selectByExample(userExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public UserResultDTO register(UserRegisterParamDTO userRegisterParamDTO) {
        User user = new User();
        UserResultDTO userResultDTO = new UserResultDTO();
        BeanUtils.copyProperties(userRegisterParamDTO, user);
        String bCryptPassword = bCryptPasswordEncoder.encode(userRegisterParamDTO.getPassword());
        user.setPassword(bCryptPassword);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        if (userMapper.insert(user) != 1) {
            return null;
        }
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
    public List<User> listAdmin(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        UserResultDTO userResultDTO = new UserResultDTO();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPidIsNull();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo pageInfo = new PageInfo<>(userList);
        return new FuncUtils().beanUtilsCopyListProperties(pageInfo.getList(), userResultDTO);
    }

    @Override
    public Object changeUserPassword(Long id, AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO) throws CustomException {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new CustomException("指定的用户不存在");
        }
        String newPassword = bCryptPasswordEncoder.encode(adminChangeUserPasswordParamDTO.getNewPassword());
        user.setPassword(newPassword);
        user.setGmtModified(new Date());
        if (userMapper.insert(user) != 1) {
            return null;
        }
//        BeanUtils.copyProperties(admin, adminResultDTO);
        return null;
    }

    @Override
    public List<Permission> getPermissionList(Long userId) {
        return userRoleRelationDao.getPermissionList(userId);
    }
}
