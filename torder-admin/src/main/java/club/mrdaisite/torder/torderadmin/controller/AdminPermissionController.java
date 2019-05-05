package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.common.api.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.PermissionInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.PermissionUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminPermissionService;
import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限控制器
 *
 * @author dai
 * @date 2019/04/18
 */
@RestController
@Api(tags = {"后台权限管理"})
@RequestMapping("/permission")
public class AdminPermissionController {
    @Autowired
    private AdminPermissionService adminPermissionService;

    @ApiOperation(value = "权限列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('permission:list')")
    public ResponseEntity listPermission() {
        return new CommonResult().success(adminPermissionService.listPermission());
    }

    @ApiOperation(value = "获取指定单个权限")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:read')")
    public ResponseEntity getPermissionById(@PathVariable Long id) throws CustomNotFoundException {
        if (!adminPermissionService.permissionExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4047000).getEMessage());
        }
        return new CommonResult().success(adminPermissionService.getPermissionById(id));
    }

    @ApiOperation(value = "添加权限")
    @PostMapping()
    @PreAuthorize("hasAuthority('permission:create')")
    public ResponseEntity insertPermission(@Validated @RequestBody PermissionInsertParamDTO permissionInsertParamDTO, BindingResult result) {
        return new CommonResult().success(adminPermissionService.insertPermission(permissionInsertParamDTO));
    }

    @ApiOperation(value = "修改权限信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity updatePermission(@PathVariable Long id, @Validated @RequestBody PermissionUpdateParamDTO permissionUpdateParamDTO, BindingResult result) throws CustomNotFoundException {
        if (!adminPermissionService.permissionExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4047000).getEMessage());
        }
        return new CommonResult().success(adminPermissionService.updatePermission(id, permissionUpdateParamDTO));
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    public void deletePermission(@PathVariable Long id) throws CustomNotFoundException {
        if (!adminPermissionService.permissionExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4047000).getEMessage());
        }
        adminPermissionService.deletePermission(id);
    }
}
