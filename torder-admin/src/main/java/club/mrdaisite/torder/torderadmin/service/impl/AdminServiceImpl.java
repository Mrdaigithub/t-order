package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dao.UserRoleRelationDao;
import club.mrdaisite.torder.torderadmin.dto.AdminInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.Permission;
import club.mrdaisite.torder.tordermbg.model.User;
import club.mrdaisite.torder.tordermbg.model.UserExample;
import club.mrdaisite.torder.tordermbg.model.UserRoleRelation;
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
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Override
    public List<User> listUser(Integer page, Integer perPage, String sortBy, String order) {
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
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO, Long roleId) {
        User user = new User();
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        UserResultDTO userResultDTO = new UserResultDTO();
        BeanUtils.copyProperties(userInsertParamDTO, user);
        String bCryptPassword = bCryptPasswordEncoder.encode(userInsertParamDTO.getPassword());
        user.setPassword(bCryptPassword);
        user.setScore(0);
        user.setEnabled(true);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insert(user);
        userRoleRelation.setUserId(user.getId());
        userRoleRelation.setRoleId(roleId);
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
    public Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO) {
        User user = userMapper.selectByPrimaryKey(id);
        String newPassword = bCryptPasswordEncoder.encode(updatePasswordParamDTO.getNewPassword());
        user.setPassword(newPassword);
        user.setGmtModified(new Date());
        return userMapper.updateByPrimaryKey(user) == 1;
    }

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
    public List<Permission> getPermissionList(Long userId) {
        return userRoleRelationDao.getPermissionList(userId);
    }
}
