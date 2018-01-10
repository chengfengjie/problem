package problem.no.model.project;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseModel;

@Getter
@Setter
public class ProductModuleModel extends BaseModel {

    private Integer moduleID;

    private Integer productID;

    private String moduleName;

}
