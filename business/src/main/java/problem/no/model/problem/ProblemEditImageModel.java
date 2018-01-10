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
public class ProblemEditImageModel extends BaseModel {

    private Integer id;

    private Integer editInfoID;

    private String imageName;

}
