package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.dto.RoleInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.RoleUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.tordermbg.mapper.AdminRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.RolePermissionRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private AdminAdminService adminAdminService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Role> listRole(Integer page, Integer perPage, String sortBy, String order) {
        return roleMapper.selectByExample(new RoleExample());
    }

    @Override
    public List<Role> listRoleByName(String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(roleName);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public Role getRoleById(Long id) throws CustomNotFoundException {
        Role role = roleMapper.selectByPrimaryKey(id);
        Optional.ofNullable(role)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4046000).getEMessage()));
        return role;
    }

    @Override
    public Role getRoleByAdminId(Long id) throws CustomNotFoundException {
        Admin admin = adminAdminService.getAdminById(id);
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andAdminIdEqualTo(admin.getId());
        List<AdminRoleRelation> adminRoleRelationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        Optional.ofNullable(adminRoleRelationList)
                .filter((adminRoleRelations) -> adminRoleRelations.size() > 0)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4041001).getEMessage()));
        return roleMapper.selectByPrimaryKey(adminRoleRelationList.get(0).getRoleId());
    }

    @Override
    public Role insertRole(RoleInsertParamDTO roleInsertParamDTO) throws CustomNotFoundException {
        Role role = new Role();
        BeanUtils.copyProperties(roleInsertParamDTO, role);
        roleMapper.insertSelective(role);
        return getRoleById(role.getId());
    }

    @Override
    public Role updateRole(Long id, RoleUpdateParamDTO roleUpdateParamDTO) throws CustomNotFoundException {
        Role role = getRoleById(id);
        BeanUtils.copyProperties(roleUpdateParamDTO, role);
        roleMapper.updateByPrimaryKeySelective(role);
        return getRoleById(id);
    }

    @Override
    public void deleteRole(Long id) throws CustomNotFoundException {
        Role role = getRoleById(id);

        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andRoleIdEqualTo(role.getId());
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);

        RolePermissionRelationExample rolePermissionRelationExample = new RolePermissionRelationExample();
        rolePermissionRelationExample.or().andRoleIdEqualTo(role.getId());
        rolePermissionRelationMapper.deleteByExample(rolePermissionRelationExample);

        roleMapper.deleteByPrimaryKey(role.getId());
    }
}
