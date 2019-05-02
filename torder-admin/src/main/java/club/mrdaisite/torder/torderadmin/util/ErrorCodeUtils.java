package club.mrdaisite.torder.torderadmin.util;

import club.mrdaisite.torder.torderadmin.exception.*;

import java.util.HashMap;

/**
 * ErrorCodeUtils
 *
 * @author dai
 * @date 2019/04/23
 */
public class ErrorCodeUtils {
    private Integer eCode;
    private String eMessage;

    /**
     * @param eC 错误码
     */
    public ErrorCodeUtils(Integer eC) {
        HashMap<Integer, String> hashMap = new HashMap<>();

        // global
        hashMap.put(4000000, "参数指定的数据已存在");
        hashMap.put(4000001, "必要的请求参数不存在");
        hashMap.put(4000002, "Http媒体类型不支持");
        hashMap.put(4000003, "路径参数不存在");
        hashMap.put(4000004, "方法参数类型不匹配");
        hashMap.put(4010000, "用户名或密码错误");
        hashMap.put(4030000, "账号未登录");
        hashMap.put(4030001, "无权限操作");
        hashMap.put(4030002, "此账号未启用");
        hashMap.put(4040000, "不允许访问");
        hashMap.put(4050000, "请求方法不支持");
        hashMap.put(5000000, "未知错误");
        hashMap.put(5000001, "错误的sql语法");
        hashMap.put(5000002, "空指针错误");
        hashMap.put(5000003, "算术异常");
        hashMap.put(5000004, "数组索引超出界限");
        hashMap.put(5000005, "插入到数据库的数据不完整");
        // admin
        hashMap.put(4041000, "不存在的管理员");
        hashMap.put(4041001, "未设定用户组的的管理员");
        hashMap.put(5001000, "添加管理员失败");
        // user
        hashMap.put(4042000, "不存在的用户");
        // merchants
        hashMap.put(4043000, "不存在的商铺");
        // message
        hashMap.put(4044000, "不存在的消息");
        // message
        hashMap.put(4045000, "不存在的配置项");
        // role
        hashMap.put(4046000, "不存在的角色组");
        // permission
        hashMap.put(4047000, "不存在的权限");

        setECode(eC);
        setEMessage(hashMap.get(eC));
    }

    public void throwBadRequestException() throws CustomBadRequestException {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        throw new CustomBadRequestException(getEMessage());
    }

    public void throwUnauthorizedException() throws CustomUnauthorizedException {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        throw new CustomUnauthorizedException(getEMessage());
    }

    public CustomForbiddenException throwForbiddenException() throws CustomForbiddenException {
        if (getEMessage() == null || getECode() == null) {
            return null;
        }
        return new CustomForbiddenException(getEMessage());
    }

    public void throwNotFoundException() throws CustomNotFoundException {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        throw new CustomNotFoundException(getEMessage());
    }

    public void throwMethodNotAllowedException() throws CustomMethodNotAllowedException {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        throw new CustomMethodNotAllowedException(getEMessage());
    }

    public void throwInternalException() throws CustomInternalException {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        throw new CustomInternalException(getEMessage());
    }

    private Integer getECode() {
        return eCode;
    }

    private void setECode(Integer eCode) {
        this.eCode = eCode;
    }

    public String getEMessage() {
        return eMessage;
    }

    private void setEMessage(String eMessage) {
        this.eMessage = eMessage;
    }
}
