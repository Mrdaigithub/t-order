package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.component.WebLogAspect;
import club.mrdaisite.torder.torderadmin.dto.AdminChangeUserPasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminRegisterParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.tordermbg.mapper.AdminMapper;
import club.mrdaisite.torder.tordermbg.model.Admin;
import club.mrdaisite.torder.tordermbg.model.AdminExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUsername(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.or().andUsernameEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public AdminResultDTO register(AdminRegisterParamDTO adminRegisterParamDTO) {
        Admin admin = new Admin();
        AdminResultDTO adminResultDTO = new AdminResultDTO();
        BeanUtils.copyProperties(adminRegisterParamDTO, admin);
        String bCryptPassword = bCryptPasswordEncoder.encode(adminRegisterParamDTO.getPassword());
        admin.setPassword(bCryptPassword);
        admin.setGmtCreate(new Date());
        admin.setGmtModified(new Date());
        if (adminMapper.insert(admin) != 1) {
            return null;
        }
        BeanUtils.copyProperties(admin, adminResultDTO);
        return adminResultDTO;
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
    public Object changeUserPassword(Long id, AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO) throws CustomException {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            throw new CustomException("指定的用户不存在");
        }
        String newPassword = bCryptPasswordEncoder.encode(adminChangeUserPasswordParamDTO.getNewPassword());
        admin.setPassword(newPassword);
        admin.setGmtModified(new Date());
        if (adminMapper.insert(admin) != 1) {
            return null;
        }
//        BeanUtils.copyProperties(admin, adminResultDTO);
        return null;
    }
}
