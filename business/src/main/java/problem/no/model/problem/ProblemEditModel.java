package problem.no.model.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseModel;

/**
 * Created by chengfj on 2017/10/23.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemEditModel extends BaseModel {

    private Integer editInfoID;

    private Integer problemID;

    /**
     * 0-创建, 1-评论, 2-指派, 3-修复, 4-拒绝, 5-关闭, 6-延期, 7-通过, 8-不通过
     */
    private Integer editType;

    private String content;

    /**
     * 1-不是错误, 2-重复问题, 3-延期解决，4-设计如此，5-不能复现，6-不同意意见，7-忽略
     */
    private Integer rejectType;

    private String auditVersionName;

    private Integer auditVersionID;

    private Integer solveUserID;

}
