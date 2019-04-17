package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserLoginParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证控制器
 *
 * @author dai
 * @date 2019/04/17
 */
@RestController
@Api(tags = {"后台用户认证"})
@RequestMapping("/auth")
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;

    @ApiOperation(value = "管理员登录返回token")
    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginParamDTO userLoginParamDTO, BindingResult result) {
        String token = adminAuthService.login(userLoginParamDTO.getUsername(), userLoginParamDTO.getPassword());
        return new CommonResult().success(token);
    }

    @ApiOperation(value = "修改管理员密码")
    @PutMapping(value = "/password/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity updateAdminPassword(@PathVariable Long id, @Validated @RequestBody UpdatePasswordParamDTO updatePasswordParamDTO, BindingResult result) {
        if (adminAuthService.updateUserPassword(id, updatePasswordParamDTO, "admin")) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping(value = "/user/password/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateUserPassword(@PathVariable Long id, @Validated @RequestBody UpdatePasswordParamDTO updatePasswordParamDTO, BindingResult result) {
        if (adminAuthService.updateUserPassword(id, updatePasswordParamDTO, "user")) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }
}
