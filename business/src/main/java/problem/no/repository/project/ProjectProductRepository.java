package problem.no.repository.project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import problem.no.model.project.ProductModuleModel;
import problem.no.model.project.ProjectProductModel;

import java.util.List;

@Repository
@Mapper
public interface ProjectProductRepository {

    void insertProduct(ProjectProductModel model);

    Boolean productIsExist(ProjectProductModel model);

    List<ProjectProductModel> queryProductList(Integer projectID);

    void insertProductModule(ProductModuleModel moduleModel);

    Boolean productModuleIsExist(ProductModuleModel moduleModel);

    List<ProductModuleModel> queryModuleList(Integer productID);

    void updateModule(ProductModuleModel moduleModel);

    void updateProduct(ProjectProductModel model);

}
