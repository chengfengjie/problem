package problem.no.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.dto.ResultDto;
import problem.no.interpretation.LoginUserPermission;
import problem.no.model.project.ProjectTagModel;
import problem.no.service.project.ProjectTagService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/project/tag")
public class ProjectTagController {

    @Autowired
    private ProjectTagService projectTagService;


    @LoginUserPermission
    @PostMapping("/create")
    public ResultDto createTag(@RequestBody @Valid ProjectTagModel model) {
        model.setLastEditUser(WebUtil.getCurrentUserID());
        model.setInUser(WebUtil.getCurrentUserID());
        projectTagService.createProjectTag(model);
        return ResultDto.msg("创建标签成功");
    }

    @LoginUserPermission
    @GetMapping("/queryList")
    public ResultDto queryProjectTagList(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectTagService.queryProjectTagList(projectID));
    }

    @LoginUserPermission
    @GetMapping("/delete")
    public ResultDto deleteTag(@RequestParam("tagID") Integer tagID) {
        projectTagService.deleteTag(tagID, WebUtil.getCurrentUserID());
        return ResultDto.msg("删除标签成功");
    }
}
