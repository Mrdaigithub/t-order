package club.mrdaisite.torderadmin.service.impl;

import club.mrdaisite.torderadmin.service.AdminService;
import club.mrdaisite.torder.tordermbg.mapper.AdminMapper;
import club.mrdaisite.torder.tordermbg.model.Admin;
import club.mrdaisite.torder.tordermbg.model.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class AdminServiceImpl implements AdminService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUsername(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.or().andUsernameEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList.get(0);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        return token;
    }
}
