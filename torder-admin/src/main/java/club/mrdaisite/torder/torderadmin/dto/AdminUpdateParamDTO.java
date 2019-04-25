package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * AdminUpdateParamDTO
 *
 * @author dai
 * @date 2019/04/16
 */
public class AdminUpdateParamDTO {
    @ApiModelProperty(value = "用户名")
    @Length(min = 4, max = 12, message = "用户名长度必须位于4到12之间")
    @Pattern(regexp = "^\\w+$", message = "用户名格式错误")
    private String username;
    @ApiModelProperty(value = "密码")
    @Size(min = 4, max = 12, message = "密码长度必须位于4到12之间")
    @Pattern(regexp = "^\\w+$", message = "密码格式错误")
    private String password;
    @ApiModelProperty(value = "启用状态")
    private Boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AdminUpdateParamDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
