package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.RoleInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.RoleUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.RolePermissionRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.UserRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Object> listRole(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Role> roleList = roleMapper.selectByExample(new RoleExample());
        PageInfo pageInfo = new PageInfo<>(roleList);
        return pageInfo.getList();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role getRoleByUserId(Long id) {
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.or().andUserIdEqualTo(id);
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.selectByExample(userRoleRelationExample);
        return roleMapper.selectByPrimaryKey(userRoleRelationList.get(0).getRoleId());
    }

    @Override
    public Role insertRole(RoleInsertParamDTO roleInsertParamDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleInsertParamDTO, role);
        role.setGmtCreate(new Date());
        role.setGmtModified(new Date());
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Long id, RoleUpdateParamDTO roleUpdateParamDTO) {
        Role role = roleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(roleUpdateParamDTO, role);
        role.setGmtModified(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.or().andRoleIdEqualTo(id);
        RolePermissionRelationExample rolePermissionRelationExample = new RolePermissionRelationExample();
        rolePermissionRelationExample.or().andRoleIdEqualTo(id);
        userRoleRelationMapper.deleteByExample(userRoleRelationExample);
        rolePermissionRelationMapper.deleteByExample(rolePermissionRelationExample);
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean roleExists(Long id) {
        return roleMapper.selectByPrimaryKey(id) != null;
    }
}
