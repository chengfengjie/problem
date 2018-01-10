package problem.no.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.dto.ResultDto;
import problem.no.dto.project.CreateModuleDto;
import problem.no.dto.project.CreateProductDto;
import problem.no.interpretation.AdminPermission;
import problem.no.interpretation.LoginUserPermission;
import problem.no.model.project.ProductModuleModel;
import problem.no.model.project.ProjectProductModel;
import problem.no.service.project.ProjectProductService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/project/product")
public class ProjectProductController {

    @Autowired
    private ProjectProductService projectProductService;

    @AdminPermission
    @PostMapping("/create")
    public ResultDto createProduct(@RequestBody @Valid CreateProductDto productDto) {
        projectProductService.createProduct(productDto, WebUtil.getCurrentUserID());
        return ResultDto.msg("添加成功");
    }

    @LoginUserPermission
    @GetMapping("/delete")
    public ResultDto deleteProduct(@RequestParam("productID") Integer productID) {
        projectProductService.deleteProduct(productID, WebUtil.getCurrentUserID());
        return ResultDto.msg("删除成功");
    }

    @LoginUserPermission
    @PostMapping("/updateProduct")
    public ResultDto updateProduct(@RequestBody @Valid ProjectProductModel model) {
        model.setLastEditUser(WebUtil.getCurrentUserID());
        projectProductService.updateProduct(model);
        return ResultDto.msg("更细产品名称成功");
    }

    @LoginUserPermission
    @GetMapping("/queryList")
    public ResultDto queryList(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectProductService.queryProductList(projectID));
    }

    @LoginUserPermission
    @PostMapping("/createModule")
    public ResultDto createModule(@RequestBody @Valid CreateModuleDto moduleDto) {
        projectProductService.createModule(moduleDto, WebUtil.getCurrentUserID());
        return ResultDto.msg("创建模块成功");
    }

    @LoginUserPermission
    @GetMapping("/queryModuleList")
    public ResultDto queryModuleList(@RequestParam("productID") Integer productID) {
        return ResultDto.data(projectProductService.queryModuleList(productID));
    }

    @LoginUserPermission
    @GetMapping("/deleteModule")
    public ResultDto deleteModule(@RequestParam("moduleID") Integer moduleID) {
        projectProductService.deleteProductModule(moduleID, WebUtil.getCurrentUserID());
        return ResultDto.msg("删除模块成功");
    }

    @LoginUserPermission
    @PostMapping("/updateModule")
    public ResultDto updateModule(@RequestBody @Valid ProductModuleModel model) {
        model.setLastEditUser(WebUtil.getCurrentUserID());
        projectProductService.updateModule(model);
        return ResultDto.msg("修改成功");
    }
}
