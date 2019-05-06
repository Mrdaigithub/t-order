package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.common.api.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.RoleInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.RoleUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色组控制器
 *
 * @author dai
 * @date 2019/03/21
 */
@RestController
@Api(tags = {"后台角色组管理"})
@RequestMapping("/role")
public class AdminRoleController {
    @Autowired
    private AdminRoleService adminRoleService;

    @ApiOperation(value = "角色组列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('role:list')")
    public ResponseEntity listRole(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminRoleService.listRole(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取指定单个角色组")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity getRoleById(@PathVariable Long id) throws CustomNotFoundException {
        return new CommonResult().success(adminRoleService.getRoleById(id));
    }

    @ApiOperation(value = "添加角色组")
    @PostMapping()
    @PreAuthorize("hasAuthority('role:create')")
    public ResponseEntity insertRole(@Validated @RequestBody RoleInsertParamDTO roleInsertParamDTO, BindingResult result) throws CustomNotFoundException {
        return new CommonResult().success(adminRoleService.insertRole(roleInsertParamDTO));
    }

    @ApiOperation(value = "修改角色组信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity updateRole(@PathVariable Long id, @Validated @RequestBody RoleUpdateParamDTO roleUpdateParamDTO, BindingResult result) throws CustomNotFoundException {
        return new CommonResult().success(adminRoleService.updateRole(id, roleUpdateParamDTO));
    }

    @ApiOperation(value = "删除角色组")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(@PathVariable Long id) throws CustomNotFoundException {
        adminRoleService.deleteRole(id);
    }
}
