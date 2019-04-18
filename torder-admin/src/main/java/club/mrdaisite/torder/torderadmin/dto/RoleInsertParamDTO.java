package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * RoleInsertParamDTO
 *
 * @author dai
 * @date 2019/03/25
 */
public class RoleInsertParamDTO {
    @ApiModelProperty(value = "角色组名称", required = true)
    @NotEmpty(message = "角色组名称不能为空")
    @Length(min = 3, max = 12, message = "角色组名称长度必须位于3到12之间")
    private String name;

    @ApiModelProperty(value = "角色组详情", required = true)
    @NotEmpty(message = "角色组详情不能为空")
    private String description;

    @ApiModelProperty(value = "角色组启用状态", required = true)
    @NotNull(message = "角色组启用状态不能为null")
    private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "RoleInsertParamDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
