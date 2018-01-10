package problem.no.dto.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateModuleDto {
    @NotNull(message = "项目必选")
    private Integer productID;
    @NotEmpty(message = "模块名称必填")
    @Length(max = 30, message = "模块名称最多30个字符")
    private String moduleName;
}
