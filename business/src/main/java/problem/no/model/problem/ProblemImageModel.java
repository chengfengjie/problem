package problem.no.model.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseModel;

@Getter
@Setter
public class ProblemImageModel extends BaseModel {

    private Integer id;

    private Integer problemID;

    private String imageName;

}
