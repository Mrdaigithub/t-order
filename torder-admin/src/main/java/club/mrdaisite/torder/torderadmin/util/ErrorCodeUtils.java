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
        // admin
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(4030000, "账号未登录");
        hashMap.put(4040000, "不存在的管理员");
        hashMap.put(5000000, "添加管理员失败");
        // user
        hashMap.put(4041000, "不存在的用户");
        // user
        hashMap.put(4042000, "不存在的商铺");
        // message
        hashMap.put(4043000, "不存在的消息");
        // message
        hashMap.put(4044000, "不存在的配置项");
        // role
        hashMap.put(4045000, "不存在的角色组");
        // permission
        hashMap.put(4046000, "不存在的权限");

        setECode(eC);
        setEMessage(hashMap.get(eC));
    }

    public void throwError() {
        if (getEMessage() == null || getECode() == null) {
            return;
        }
        switch (getECode().toString().substring(0, 3)) {
            case "400":
                try {
                    throw new CustomBadRequestException(eMessage);
                } catch (CustomBadRequestException e) {
                    e.printStackTrace();
                }
            case "401":
                try {
                    throw new CustomUnauthorizedException(eMessage);
                } catch (CustomUnauthorizedException e) {
                    e.printStackTrace();
                }
            case "403":
                try {
                    throw new CustomForbiddenException(eMessage);
                } catch (CustomForbiddenException e) {
                    e.printStackTrace();
                }
            case "404":
                try {
                    throw new CustomNotFoundException(eMessage);
                } catch (CustomNotFoundException e) {
                    e.printStackTrace();
                }
            case "500":
                try {
                    throw new CustomInternalException(eMessage);
                } catch (CustomInternalException e) {
                    e.printStackTrace();
                }
            default:
                try {
                    throw new CustomInternalException("未知错误");
                } catch (CustomInternalException e) {
                    e.printStackTrace();
                }
        }
    }

    private Integer getECode() {
        return eCode;
    }

    private void setECode(Integer eCode) {
        this.eCode = eCode;
    }

    private String getEMessage() {
        return eMessage;
    }

    private void setEMessage(String eMessage) {
        this.eMessage = eMessage;
    }
}
