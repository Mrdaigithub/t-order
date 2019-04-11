package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.*;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity test() {
        Object permissionList = adminService.getPermissionList(1L);
        return new CommonResult().success(permissionList.toString());
    }

    @ApiOperation(value = "管理员列表")
    @GetMapping(value = "/")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity listAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                    @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                    @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().pageSuccess(Collections.singletonList(adminService.listAdmin(page, perPage, sortBy, order)));
    }

    @ApiOperation(value = "管理员注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterParamDTO userRegisterParamDTO, BindingResult result) {
        UserResultDTO userResultDTO = adminService.register(userRegisterParamDTO);
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

    @ApiOperation(value = "管理员修改用户密码")
    @PutMapping(value = "/user/password/{id}")
    public Object changeUserPassword(@PathVariable Long id, @Validated @RequestBody AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO, BindingResult result) throws CustomException {
        adminService.changeUserPassword(id, adminChangeUserPasswordParamDTO);
        return null;
    }
}
