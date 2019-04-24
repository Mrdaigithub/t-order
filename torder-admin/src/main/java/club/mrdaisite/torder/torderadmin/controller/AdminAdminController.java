package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.AdminInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.exception.CustomForbiddenException;
import club.mrdaisite.torder.torderadmin.exception.CustomInternalException;
import club.mrdaisite.torder.torderadmin.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import club.mrdaisite.torder.tordermbg.model.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 管理员控制器
 *
 * @author dai
 * @date 2019/03/21
 */
@RestController
@Api(tags = {"后台管理员管理"})
@RequestMapping("/admin")
public class AdminAdminController {
    @Autowired
    private AdminAdminService adminAdminService;

    @ApiOperation(value = "管理员列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('admin:list')")
    public ResponseEntity listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminAdminService.listAdmin(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取当前登录管理员")
    @GetMapping(value = "/info")
    public ResponseEntity getInfo(Principal principal) throws CustomForbiddenException, CustomNotFoundException {
        if (principal == null) {
            new ErrorCodeUtils(4030000).throwForbiddenException();
        }
        assert principal != null;
        Admin admin = adminAdminService.getAdminByUsername(principal.getName());
        AdminResultDTO adminResultDTO = new AdminResultDTO();
        BeanUtils.copyProperties(admin, adminResultDTO);
        return new CommonResult().success(adminResultDTO);
    }

    @ApiOperation(value = "获取指定单个管理员")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity getUserById(@PathVariable Long id) throws CustomNotFoundException {
        return new CommonResult().success(adminAdminService.getAdminById(id));
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping()
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity insertAdmin(@Validated @RequestBody AdminInsertParamDTO adminInsertParamDTO, BindingResult result) throws CustomInternalException {
        AdminResultDTO adminResultDTO = adminAdminService.insertAdmin(adminInsertParamDTO, "admin");
        if (adminResultDTO == null) {
            new ErrorCodeUtils(5001000).throwInternalException();
        }
        return new CommonResult().success(adminResultDTO);
    }

    @ApiOperation(value = "修改管理员信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity updateAdmin(@PathVariable Long id, @Validated @RequestBody AdminUpdateParamDTO adminUpdateParamDTO, BindingResult result) throws CustomNotFoundException {
        return new CommonResult().success(adminAdminService.updateAdmin(id, adminUpdateParamDTO));
    }

    @ApiOperation(value = "删除管理员")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteAdmin(@PathVariable Long id) throws CustomNotFoundException {
        adminAdminService.deleteAdmin(id);
    }
}
