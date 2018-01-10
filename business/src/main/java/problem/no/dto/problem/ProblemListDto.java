package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.problem.ProblemModel;

import java.util.List;

/**
 * Created by chengfj on 2017/10/20.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemListDto extends ProblemModel {

    private String solveUserName;

    private String inUserName;

    private String projectName;

    private String productName;

    private String moduleName;

    private List<ProblemTagDto> tagList;
}
