package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * UserInsertParamDTO
 *
 * @author dai
 * @date 2019/03/25
 */
public class UserInsertParamDTO {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 12, message = "用户名长度必须位于4到12之间")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    @Size(min = 4, max = 12, message = "密码长度必须位于4到12之间")
    private String password;
    @ApiModelProperty(value = "银行卡号")
    @NotEmpty(message = "银行卡号不能为空")
    @Pattern(regexp = "^([1-9])(\\d{15}|\\d{18})$", message = "银行卡号格式错误")
    private String bankCard;

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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    @Override
    public String toString() {
        return "UserInsertParamDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bankCard='" + bankCard + '\'' +
                '}';
    }
}