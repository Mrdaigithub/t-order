package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.dto.UserUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.UserMapper;
import club.mrdaisite.torder.tordermbg.model.User;
import club.mrdaisite.torder.tordermbg.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private UserMapper userMapper;

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
    public List<User> listUser() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public UserResultDTO getUserById(Long id) {
        UserResultDTO userResultDTO = new UserResultDTO();
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            new ErrorCodeUtils(4041000).throwError();
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
    public UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO) {
        User user = new User();
        UserResultDTO userResultDTO = new UserResultDTO();

        BeanUtils.copyProperties(userInsertParamDTO, user);

        String bCryptPassword = bCryptPasswordEncoder.encode(userInsertParamDTO.getPassword());
        user.setPassword(bCryptPassword);
        user.setScore(0);
        user.setEnabled(true);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insert(user);
        BeanUtils.copyProperties(user, userResultDTO);
        return userResultDTO;
    }

    @Override
    public UserResultDTO updateUser(Long id, UserUpdateParamDTO userUpdateParamDTO) throws AccessDeniedException{
        User user = userMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(userUpdateParamDTO, user);
        if (user.getPid() == null) {
            user.setPid(null);
        }
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return getUserById(id);
    }

    @Override
    public void deleteUser(Long id) throws AccessDeniedException {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean userExists(Long id) {
        return userMapper.selectByPrimaryKey(id) != null;
    }
}
