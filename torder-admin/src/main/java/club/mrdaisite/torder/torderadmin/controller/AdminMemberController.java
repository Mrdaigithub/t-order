package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.MemberUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminMemberService;
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
@RequestMapping("/member")
public class AdminMemberController {
    @Autowired
    private AdminMemberService adminMemberService;

    @ApiOperation(value = "用户分页列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('member:list')")
    public ResponseEntity listMember(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                     @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                     @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminMemberService.listMember(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取指定单个用户")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('member:read')")
    public ResponseEntity getMemberById(@PathVariable Long id) throws CustomNotFoundException {
        return new CommonResult().success(adminMemberService.getMemberById(id));
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping(value = "/member/{id}")
    @PreAuthorize("hasAuthority('member:update')")
    public ResponseEntity updateMember(@PathVariable Long id, @Validated @RequestBody MemberUpdateParamDTO memberUpdateParamDTO, BindingResult result) throws CustomNotFoundException {
        if (!adminMemberService.memberExists(id)) {
            new ErrorCodeUtils(4042000).throwNotFoundException();
        }
        return new CommonResult().success(adminMemberService.updateMember(id, memberUpdateParamDTO));
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/member/{id}")
    @PreAuthorize("hasAuthority('member:delete')")
    public void deleteMember(@PathVariable Long id) throws CustomNotFoundException {
        if (!adminMemberService.memberExists(id)) {
            new ErrorCodeUtils(4042000).throwNotFoundException();
        }
        adminMemberService.deleteMember(id);
    }
}
