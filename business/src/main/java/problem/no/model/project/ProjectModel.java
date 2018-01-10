package problem.no.model.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import problem.no.dto.project.ProjectMemberDto;
import problem.no.model.BaseModel;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProjectModel extends BaseModel {

    private Integer projectID;

    @NotBlank(message = "项目名称必填")
    @Length(max = 50, message = "项目名称最大50个字符")
    private String projectName;

    @Length(max = 200, message = "项目名称最大200个字符")
    private String projectDescription;

    private Date beginDate;

    private Date finishDate;

    @NotNull(message = "项目状态不能为空")
    private Integer status;

    private List<ProjectMemberDto> memberData;

    private List<ProjectProductModel> productData;
}
