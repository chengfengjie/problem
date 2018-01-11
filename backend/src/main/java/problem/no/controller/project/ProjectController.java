package problem.no.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.dto.ResultDto;
import problem.no.exception.PNException;
import problem.no.interpretation.AdminPermission;
import problem.no.interpretation.LoginUserPermission;
import problem.no.model.project.ProjectModel;
import problem.no.service.project.ProjectService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @LoginUserPermission
    @PostMapping("/create")
    public ResultDto createProject(@RequestBody @Valid ProjectModel projectModel) {
        projectModel.setInUser(WebUtil.getCurrentUserID());
        projectModel.setLastEditUser(WebUtil.getCurrentUserID());
        projectService.createProject(projectModel, WebUtil.getCurrentUserName());
        return ResultDto.data(projectModel);
    }

    @LoginUserPermission
    @GetMapping("/queryAllProject")
    public ResultDto queryProjectList() {
        return ResultDto.data(projectService.queryProjectList(WebUtil.getCurrentUserID()));
    }

    @LoginUserPermission
    @GetMapping("/queryById")
    public ResultDto queryById(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectService.queryProjectById(projectID));
    }

    @LoginUserPermission
    @PostMapping("/update")
    public ResultDto updateProject(@RequestBody @Valid ProjectModel projectModel) {
        if (projectModel.getProjectID() == null) {
            throw new PNException("项目ID不能为空");
        }
        projectService.updateProject(projectModel);
        return ResultDto.msg("编辑项目成功");
    }

    @LoginUserPermission
    @GetMapping("/general")
    public ResultDto projectGenreal(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectService.queryProjectGeneral(projectID));
    }

    @LoginUserPermission
    @GetMapping("/notice")
    public ResultDto noticeInfo(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectService.selectNoticeInfo(projectID, WebUtil.getCurrentUserID()));
    }
}
