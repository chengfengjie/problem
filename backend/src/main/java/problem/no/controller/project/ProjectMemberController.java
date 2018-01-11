package problem.no.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.dto.ResultDto;
import problem.no.interpretation.AdminPermission;
import problem.no.interpretation.LoginUserPermission;
import problem.no.model.project.ProjectMemberModel;
import problem.no.service.project.ProjectMemberService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/project/member")
public class ProjectMemberController {

    @Autowired
    private ProjectMemberService projectMemberService;

    @LoginUserPermission
    @PostMapping("/add")
    public ResultDto addMember(@RequestBody @Valid ProjectMemberModel model) {
        model.setInUser(WebUtil.getCurrentUserID());
        model.setLastEditUser(WebUtil.getCurrentUserID());
        projectMemberService.createProjectMember(model);
        return ResultDto.msg("添加成员成功");
    }

    @LoginUserPermission
    @GetMapping("/queryProjectMemberList")
    public ResultDto queryProjectMemberList(@RequestParam("projectID") Integer projectID) {
        return ResultDto.data(projectMemberService.queryProjectMemberList(projectID));
    }

    @LoginUserPermission
    @GetMapping("/delete")
    public ResultDto deleteProjectMember(@RequestParam("projectID") Integer projectID,
                                         @RequestParam("userID") Integer userID) {
        projectMemberService.deleteProjectMember(projectID, userID, WebUtil.getCurrentUserID());
        return ResultDto.msg("删除成功");
    }
}
