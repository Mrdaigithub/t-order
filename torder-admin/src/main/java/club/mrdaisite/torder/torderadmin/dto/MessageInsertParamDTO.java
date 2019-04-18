package club.mrdaisite.torder.torderadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * MessageInsertParamDTO
 *
 * @author dai
 * @date 2019/04/18
 */
public class MessageInsertParamDTO {
    @ApiModelProperty(value = "消息名称", required = true)
    @NotEmpty(message = "消息名称不能为空")
    private String name;

    @ApiModelProperty(value = "消息详情", required = true)
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
        return "MessageInsertParamDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
