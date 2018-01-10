package problem.no.model.problem.query;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseQueryFilter;

/**
 * @author chengfj
 */
@Getter
@Setter
public class ProblemQueryFilter extends BaseQueryFilter {
    private Integer projectID;
    private Integer productID;
    private Integer moduleID;
    private Integer problemType;
    private Integer inUserID;
    private Integer solveUserID;
    private Integer priority;
    private String batchStatus;
}
