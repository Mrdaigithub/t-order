package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.component.WebLogAspect;
import club.mrdaisite.torder.torderadmin.dto.*;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 *
 * @author dai
 * @date 2019/03/21
 */
@RestController
@Api(tags = "AdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "test")
    @GetMapping(value = "/test")
    public ResponseEntity test() {
        Object permissionList = adminService.getPermissionList(1L);
        LOGGER.warn(permissionList.toString());
        return new CommonResult().success("test");
    }

    @ApiOperation(value = "管理员注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserRegisterParamDTO userRegisterParamDTO, BindingResult result) {
        UserResultDTO userResultDTO = adminService.register(userRegisterParamDTO);
        if (userResultDTO == null) {
            return new CommonResult().internalServerError(null);
        }
        return new CommonResult().success(userResultDTO);
    }

    @ApiOperation(value = "管理员登录返回token")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserLoginParamDTO userLoginParamDTO, BindingResult result) {
        String token = adminService.login(userLoginParamDTO.getUsername(), userLoginParamDTO.getPassword());
        return new CommonResult().success(token);
    }

    @ApiOperation(value = "管理员修改用户密码")
    @PutMapping(value = "/user/password/{id}")
    public Object changeUserPassword(@PathVariable Long id, @RequestBody AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO, BindingResult result) throws CustomException {
        adminService.changeUserPassword(id, adminChangeUserPasswordParamDTO);
        return null;
    }
}
