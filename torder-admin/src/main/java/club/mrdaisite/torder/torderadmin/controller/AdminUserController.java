package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/user")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation(value = "用户分页列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('user:list')")
    public ResponseEntity listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminUserService.listUser(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取指定单个用户")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity getUserById(@PathVariable Long id) {
        return new CommonResult().success(adminUserService.getUserById(id));
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/user")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity insertUser(@Validated @RequestBody UserInsertParamDTO userInsertParamDTO, BindingResult result) {
        return new CommonResult().success(adminUserService.insertUser(userInsertParamDTO));
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping(value = "/user/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateUser(@PathVariable Long id, @Validated @RequestBody UserUpdateParamDTO userUpdateParamDTO, BindingResult result) {
        if (!adminUserService.userExists(id)) {
            new ErrorCodeUtils(4041000).throwError();
        }
        return new CommonResult().success(adminUserService.updateUser(id, userUpdateParamDTO));
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUser(@PathVariable Long id) {
        if (!adminUserService.userExists(id)) {
            new ErrorCodeUtils(4041000).throwError();
        }
        adminUserService.deleteUser(id);
    }
}
