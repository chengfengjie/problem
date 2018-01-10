package problem.no.model.problem;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import problem.no.model.BaseModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author chengfj
 */
@Getter
@Setter
public class ProblemModel extends BaseModel {

    private Integer problemID;

    @NotNull(message = "项目ID不能为空")
    private Integer projectID;

    private Integer productID;

    private Integer problemType;

    private Integer moduleID;

    @NotEmpty(message = "问题标题不能为空")
    private String problemTitle;

    private String versionName;

    private Integer versionID;

    private String environment;

    @Length(max = 2000, message = "问题描述最多2000个字")
    private String description;

    private Integer solveUserID;

    private Integer priority;

    private Date beginDate;

    private Date finishDate;

    private String planVersion;

    private Integer status;

}
