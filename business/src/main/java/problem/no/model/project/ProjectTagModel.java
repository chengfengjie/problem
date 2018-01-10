package problem.no.model.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import problem.no.model.BaseModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectTagModel extends BaseModel {

    private Integer tagID;

    @NotNull(message = "项目ID不能为空")
    private Integer projectID;

    @NotEmpty(message = "标签不能为空")
    @Length(max = 20, message = "标签名称最多20个字")
    private String tagName;

    @NotEmpty(message = "标签颜色不能为空")
    @Length(max = 10, message = "标签颜色最多20个字")
    private String tagColor;

}
