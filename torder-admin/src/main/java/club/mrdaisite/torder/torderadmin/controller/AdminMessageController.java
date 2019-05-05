package club.mrdaisite.torder.torderadmin.controller;

import club.mrdaisite.torder.common.api.CommonResult;
import club.mrdaisite.torder.torderadmin.dto.MessageInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.MessageUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.torderadmin.service.AdminMessageService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import club.mrdaisite.torder.tordermbg.model.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 消息控制器
 *
 * @author dai
 * @date 2019/04/18
 */
@RestController
@Api(tags = {"后台消息管理"})
@RequestMapping("/message")
public class AdminMessageController {
    @Autowired
    private AdminMessageService adminMessageService;
    @Autowired
    private AdminAdminService adminAdminService;

    @ApiOperation(value = "消息列表")
    @GetMapping()
    @PreAuthorize("hasAuthority('message:list')")
    public ResponseEntity listMessage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "perPage", defaultValue = "10") Integer perPage,
                                      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                      @RequestParam(value = "order", defaultValue = "asc") String order) {
        return new CommonResult().success(adminMessageService.listMessage(page, perPage, sortBy, order));
    }

    @ApiOperation(value = "获取指定单个消息")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('message:read')")
    public ResponseEntity getUserById(@PathVariable Long id) throws CustomNotFoundException {
        if (!adminMessageService.messageExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4044000).getEMessage());
        }
        return new CommonResult().success(adminMessageService.getMessageById(id));
    }

    @ApiOperation(value = "添加消息")
    @PostMapping()
    @PreAuthorize("hasAuthority('message:create')")
    public ResponseEntity insertMessage(@Validated @RequestBody MessageInsertParamDTO messageInsertParamDTO, BindingResult result, Principal principal) throws CustomNotFoundException {
        Admin admin = adminAdminService.getAdminByUsername(principal.getName());
        adminAdminService.adminExists(admin.getId());
        return new CommonResult().success(adminMessageService.insertMessage(messageInsertParamDTO, admin.getId()));
    }

    @ApiOperation(value = "修改消息信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('message:update')")
    public ResponseEntity updateMessage(@PathVariable Long id, @Validated @RequestBody MessageUpdateParamDTO messageUpdateParamDTO, BindingResult result) throws CustomNotFoundException {
        if (!adminMessageService.messageExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4044000).getEMessage());
        }
        return new CommonResult().success(adminMessageService.updateMessage(id, messageUpdateParamDTO));
    }

    @ApiOperation(value = "删除消息")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('message:delete')")
    public void deleteMessage(@PathVariable Long id) throws CustomNotFoundException {
        if (!adminMessageService.messageExists(id)) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4044000).getEMessage());
        }
        adminMessageService.deleteMessage(id);
    }
}
