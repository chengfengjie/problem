package problem.no.dto.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateProductDto {

    @NotNull(message = "项目ID必须填写")
    private Integer projectID;

    @NotEmpty(message = "项目名称")
    private String productName;

}
