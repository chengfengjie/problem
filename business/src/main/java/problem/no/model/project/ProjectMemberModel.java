package problem.no.model.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import problem.no.model.BaseModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectMemberModel extends BaseModel {

    private Integer id;

    @NotNull(message = "用户ID不能为空")
    private Integer userID;

    @NotNull(message = "项目ID不能为空")
    private Integer projectID;

    private Boolean isOwner;

    private Boolean isAdmin;

    @Length(max = 50, message = "显示名称最多50个字符")
    @NotEmpty(message = "显示名称必须填写")
    private String displayName;

}
