package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * 管理员修改密码参数
 *
 * @author dai
 * @date 2019/03/25
 */
public class UpdatePasswordParamDTO {
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UpdatePasswordParamDTO{" +
                "newPassword='" + newPassword + '\'' +
                '}';
    }
}
