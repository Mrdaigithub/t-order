package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * MemberUpdateParamDTO
 *
 * @author dai
 * @date 2019/04/16
 */
public class MemberUpdateParamDTO {
    @ApiModelProperty(value = "用户名")
    @Length(min = 4, max = 12, message = "用户名长度必须位于4到12之间")
    @Pattern(regexp = "^\\w+$", message = "用户名格式错误")
    private String username;
    @ApiModelProperty(value = "密码")
    @Size(min = 4, max = 12, message = "密码长度必须位于4到12之间")
    @Pattern(regexp = "^\\w+$", message = "密码格式错误")
    private String password;
    @ApiModelProperty(value = "银行卡号")
    @Pattern(regexp = "^([1-9])(\\d{15}|\\d{18})$", message = "银行卡号格式错误")
    private String bankCard;
    @ApiModelProperty(value = "积分")
    @Min(value = 0, message = "积分不能为负数")
    private Integer score;
    @ApiModelProperty(value = "引荐人id")
    @Min(value = 1L, message = "引荐人id格式不正确")
    private Long pid;
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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "MemberUpdateParamDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", score=" + score +
                ", pid=" + pid +
                ", enabled=" + enabled +
                '}';
    }
}
