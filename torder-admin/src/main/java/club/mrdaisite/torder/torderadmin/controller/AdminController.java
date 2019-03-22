package club.mrdaisite.torderadmin.controller;

import club.mrdaisite.torderadmin.dto.CommonLoginParam;
import club.mrdaisite.torderadmin.dto.CommonResult;
import club.mrdaisite.torderadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "管理员登录返回token")
    @PostMapping(value = "/login")
    public CommonResult login(@RequestBody CommonLoginParam commonLoginParam, BindingResult bindingResult) {
        String token = adminService.login(commonLoginParam.getUsername(), commonLoginParam.getPassword());
        if (token == null) {
            return new CommonResult().failed();
        }
        return new CommonResult().success(token);
    }
}
