package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.problem.ProblemModel;

import java.util.List;

/**
 * @author chengfenegjie
 */

@Getter
@Setter
public class ProblemFormDto extends ProblemModel {

    private List<String> imageList;

    private List<Integer> tagList;
}
