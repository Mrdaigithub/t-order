package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.*;
import club.mrdaisite.torder.torderadmin.service.AdminService;
import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import club.mrdaisite.torder.tordermbg.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;

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
    public ResponseEntity test(Principal principal) {
        LoggerUtil.logger.warn(principal.getName());
        Object permissionList = adminService.getPermissionListByUsername("root");
        return new CommonResult().success(permissionList.toString());
    }

    @ApiOperation(value = "用户列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminService.listUser(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取当前登录用户")
    @GetMapping(value = "/info")
    public ResponseEntity getInfo(Principal principal) {
        User user = adminService.getUserByUsername(principal.getName());
        UserResultDTO userResultDTO = new UserResultDTO();
        BeanUtils.copyProperties(user, userResultDTO);
        return new CommonResult().success(userResultDTO);
    }

    @ApiOperation(value = "获取指定单个用户")
    @GetMapping(value = "/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) throws CustomException {
        return new CommonResult().success(adminService.getUserById(id));
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping()
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity insertAdmin(@Validated @RequestBody AdminInsertParamDTO adminInsertParamDTO, BindingResult result) {
        UserInsertParamDTO userInsertParamDTO = new UserInsertParamDTO();
        BeanUtils.copyProperties(adminInsertParamDTO, userInsertParamDTO);
        UserResultDTO userResultDTO = adminService.insertUser(userInsertParamDTO, "admin");
        if (userResultDTO == null) {
            return new CommonResult().internalServerError(null);
        }
        return new CommonResult().success(userResultDTO);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/user")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity insertUser(@Validated @RequestBody UserInsertParamDTO userInsertParamDTO, BindingResult result) {
        return new CommonResult().success(adminService.insertUser(userInsertParamDTO, "user"));
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
        if (adminService.updateUserPassword(id, updatePasswordParamDTO, "admin")) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping(value = "/user/password/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateUserPassword(@PathVariable Long id, @Validated @RequestBody UpdatePasswordParamDTO updatePasswordParamDTO, BindingResult result) {
        if (adminService.updateUserPassword(id, updatePasswordParamDTO, "user")) {
            return new CommonResult().success(null);
        }
        return new CommonResult().badRequest(null);
    }

    @ApiOperation(value = "修改管理员信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity updateAdmin(@PathVariable Long id, @Validated @RequestBody UserUpdateParamDTO userUpdateParamDTO, BindingResult result) throws InvocationTargetException, IllegalAccessException {
        return new CommonResult().success(adminService.updateUser(id, userUpdateParamDTO, "admin"));
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping(value = "/user/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateUser(@PathVariable Long id, @Validated @RequestBody UserUpdateParamDTO userUpdateParamDTO, BindingResult result) throws InvocationTargetException, IllegalAccessException {
        return new CommonResult().success(adminService.updateUser(id, userUpdateParamDTO, "user"));
    }

    @ApiOperation(value = "删除管理员")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteUser(id, "admin");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id, "user");
    }
}
