package problem.no.model.project;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseModel;

@Getter
@Setter
public class ProjectProductModel extends BaseModel {

    private Integer productID;

    private Integer projectID;

    private String productName;

    private Boolean isOpen;

}
