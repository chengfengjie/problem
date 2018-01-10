package problem.no.service.project;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.dto.project.CreateModuleDto;
import problem.no.dto.project.CreateProductDto;
import problem.no.exception.PNException;
import problem.no.model.project.ProductModuleModel;
import problem.no.model.project.ProjectProductModel;
import problem.no.repository.project.ProjectProductRepository;

import java.util.List;

@Service
public class ProjectProductService {

    @Autowired
    private ProjectProductRepository projectProductRepository;

    public ProjectProductModel createProduct(CreateProductDto productDto, Integer editUserID) {
        ProjectProductModel model = new ProjectProductModel();
        model.setProjectID(productDto.getProjectID());
        model.setProductName(productDto.getProductName());
        model.setIsOpen(true);
        model.setInUser(editUserID);
        model.setLastEditUser(editUserID);

        if (projectProductRepository.productIsExist(model)) {
            throw new PNException("产品名称已经存");
        }
        projectProductRepository.insertProduct(model);
        return model;
    }

    public void deleteProduct(Integer productID, Integer editUserID) {
        ProjectProductModel model = new ProjectProductModel();
        model.setIsActive(false);
        model.setProductID(productID);
        model.setLastEditUser(editUserID);
        projectProductRepository.updateProduct(model);
    }

    public void updateProduct(ProjectProductModel model) {
        if (Strings.isNullOrEmpty(model.getProductName())) {
            throw new PNException("产品名称不能为空");
        }
        if (model.getProductID() == null) {
            throw new PNException("产品ID不能为空");
        }
        if (model.getProjectID() == null) {
            throw new PNException("项目ID不能为空");
        }
        if (projectProductRepository.productIsExist(model)) {
            throw new PNException("产品名称已经存在");
        }
        projectProductRepository.updateProduct(model);
    }

    public List<ProjectProductModel> queryProductList(Integer projectID) {
        return projectProductRepository.queryProductList(projectID);
    }

    public ProductModuleModel createModule(CreateModuleDto createModuleDto, Integer editUserID) {
        ProductModuleModel moduleModel = new ProductModuleModel();
        moduleModel.setProductID(createModuleDto.getProductID());
        moduleModel.setModuleName(createModuleDto.getModuleName());
        moduleModel.setInUser(editUserID);
        moduleModel.setLastEditUser(editUserID);
        if (projectProductRepository.productModuleIsExist(moduleModel)) {
            throw new PNException("模块已经存在");
        }
        projectProductRepository.insertProductModule(moduleModel);
        return moduleModel;
    }

    public List<ProductModuleModel> queryModuleList(Integer productID) {
        return projectProductRepository.queryModuleList(productID);
    }

    public void deleteProductModule(Integer moduleID, Integer editUserID) {
        ProductModuleModel model = new ProductModuleModel();
        model.setLastEditUser(editUserID);
        model.setIsActive(false);
        model.setModuleID(moduleID);
        projectProductRepository.updateModule(model);
    }

    public void updateModule(ProductModuleModel model) {
        if (Strings.isNullOrEmpty(model.getModuleName())) {
            throw new PNException("模块名称不能为空");
        }
        if (model.getModuleID() == null) {
            throw new PNException("模块ID不能为空");
        }
        if (model.getLastEditUser() == null) {
            throw new PNException("更新用户不能为空");
        }
        if (projectProductRepository.productModuleIsExist(model)) {
            throw new PNException("模块已经存在");
        }
        projectProductRepository.updateModule(model);
    }
}
