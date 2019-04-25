package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.MemberLoginParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity login(@Validated @RequestBody MemberLoginParamDTO memberLoginParamDTO, BindingResult result) {
        String token = adminAuthService.login(memberLoginParamDTO.getUsername(), memberLoginParamDTO.getPassword());
        return new CommonResult().success(token);
    }
}
