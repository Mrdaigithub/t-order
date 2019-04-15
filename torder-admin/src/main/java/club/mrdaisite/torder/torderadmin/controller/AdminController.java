package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.*;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 *
 * @author dai
 * @date 2019/03/21
 */
@RestController
@Api(tags = {"后台用户管理"})
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "test")
    @GetMapping(value = "/test")
    public ResponseEntity test() {
        Object permissionList = adminService.getPermissionList(1L);
        return new CommonResult().success(permissionList.toString());
    }

    @ApiOperation(value = "用户列表")
    @GetMapping(value = "/")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminService.listUser(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取指定单个用户")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:read') and hasAuthority('user:read')")
    public ResponseEntity getUserById(@PathVariable Long id) {
        return new CommonResult().success(adminService.getUserById(id));
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity insertAdmin(@Validated @RequestBody AdminInsertParamDTO adminInsertParamDTO, BindingResult result) {
        UserInsertParamDTO userInsertParamDTO = new UserInsertParamDTO();
        BeanUtils.copyProperties(adminInsertParamDTO, userInsertParamDTO);
        UserResultDTO userResultDTO = adminService.insertUser(userInsertParamDTO, 2L);
        if (userResultDTO == null) {
            return new CommonResult().internalServerError(null);
        }
        return new CommonResult().success(userResultDTO);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/user/")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity insertUser(@Validated @RequestBody UserInsertParamDTO userInsertParamDTO, BindingResult result) {
        UserResultDTO userResultDTO = adminService.insertUser(userInsertParamDTO, 3L);
        if (userResultDTO == null) {
            return new CommonResult().internalServerError(null);
        }
        return new CommonResult().success(userResultDTO);
    }

    @ApiOperation(value = "管理员登录返回token")
    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginParamDTO userLoginParamDTO, BindingResult result) {
        String token = adminService.login(userLoginParamDTO.getUsername(), userLoginParamDTO.getPassword());
        return new CommonResult().success(token);
    }

    @ApiOperation(value = "修改管理员密码")
    @PutMapping(value = "/password/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity updateAdminPassword(@PathVariable Long id, @Validated @RequestBody UpdatePasswordParamDTO updatePasswordParamDTO, BindingResult result) {
        if (adminService.updateUserPassword(id, updatePasswordParamDTO)) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping(value = "/password/user/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateUserPassword(@PathVariable Long id, @Validated @RequestBody UpdatePasswordParamDTO updatePasswordParamDTO, BindingResult result) {
        if (adminService.updateUserPassword(id, updatePasswordParamDTO)) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }
}
