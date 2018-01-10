package problem.no.model.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseModel;

/**
 * Created by chengfj on 2017/10/30.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemTagModel extends BaseModel {

    private Integer id;

    private Integer problemID;

    private Integer tagID;

}
