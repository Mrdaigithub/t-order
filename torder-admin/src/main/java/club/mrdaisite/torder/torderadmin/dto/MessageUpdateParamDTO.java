package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * MessageUpdateParamDTO
 *
 * @author dai
 * @date 2019/04/18
 */
public class MessageUpdateParamDTO {
    @ApiModelProperty(value = "消息名称")
    @NotEmpty(message = "消息名称不能为空")
    private String name;

    @ApiModelProperty(value = "消息详情")
    @NotNull(message = "消息详情不能为null")
    private String description;

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

    @Override
    public String toString() {
        return "MessageUpdateParamDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
