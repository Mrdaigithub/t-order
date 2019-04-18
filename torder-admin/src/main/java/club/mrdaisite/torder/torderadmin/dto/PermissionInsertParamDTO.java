package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * PermissionInsertParamDTO
 *
 * @author dai
 * @date 2019/04/18
 */
public class PermissionInsertParamDTO {
    @ApiModelProperty(value = "权限名称", required = true)
    @NotEmpty(message = "权限名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限值", required = true)
    @NotEmpty(message = "权限详情不能为空")
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
        return "PermissionInsertParamDTO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
