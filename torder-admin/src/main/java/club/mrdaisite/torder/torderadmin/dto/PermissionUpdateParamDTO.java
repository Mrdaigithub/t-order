package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * PermissionUpdateParamDTO
 *
 * @author dai
 * @date 2019/04/18
 */
public class PermissionUpdateParamDTO {
    @ApiModelProperty(value = "权限名称")
    @NotEmpty(message = "权限名称不能为空")
    @Length(min = 3, max = 12, message = "权限名称长度必须位于3到12之间")
    private String name;

    @ApiModelProperty(value = "权限值")
    @NotEmpty(message = "权限值不能为空")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PermissionUpdateParamDTO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
