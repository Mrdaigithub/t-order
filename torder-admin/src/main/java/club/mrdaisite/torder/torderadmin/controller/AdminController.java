package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.CommonLoginParam;
import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController
 *
 * @author dai
 * @date 2019/03/21
 */
@RestController
@Api(tags = "AdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "test")
    @GetMapping(value = "/test")
    public CommonResult test() throws Exception {
        throw new Exception("xxx");
//        return new CommonResult().success("test");
    }

    @ApiOperation(value = "管理员登录返回token")
    @PostMapping(value = "/login")
    public CommonResult login(@RequestBody CommonLoginParam commonLoginParam, BindingResult bindingResult) throws Exception {
        String token = adminService.login(commonLoginParam.getUsername(), commonLoginParam.getPassword());
        return new CommonResult().success(token);
    }
}
