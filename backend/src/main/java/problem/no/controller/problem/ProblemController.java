package problem.no.controller.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problem.no.dto.ResultDto;
import problem.no.dto.problem.*;
import problem.no.exception.PNException;
import problem.no.interpretation.LoginUserPermission;
import problem.no.model.problem.query.ProblemQueryFilter;
import problem.no.service.problem.ProblemEditService;
import problem.no.service.problem.ProblemService;
import problem.no.util.WebUtil;

import javax.validation.Valid;

/**
 * @author chengfj
 */
@RestController
@RequestMapping("/api/v1/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemEditService problemEditService;

    @LoginUserPermission
    @PostMapping("/create")
    public ResultDto createProblem(@RequestBody @Valid ProblemFormDto problemFormDto) {
        problemFormDto.setInUser(WebUtil.getCurrentUserID());
        problemFormDto.setLastEditUser(WebUtil.getCurrentUserID());
        problemService.createProblem(problemFormDto);
        return ResultDto.data(problemFormDto.getProblemID());
    }

    @LoginUserPermission
    @PostMapping("/queryList")
    public ResultDto queryProblemList(@RequestBody @Valid ProblemQueryFilter queryFilter) {
        return ResultDto.data(problemService.queryProblemList(queryFilter));
    }

    @LoginUserPermission
    @GetMapping("/query")
    public ResultDto queryProblemById(@RequestParam("problemID") Integer problemID,
                                      @RequestParam(value = "projectID", required = false) Integer projectID) {
        ProblemDetailDto detailDto = problemService.queryProblemById(problemID, WebUtil.getCurrentUserID(), projectID);
        if (detailDto == null) {
            return ResultDto.warning(new PNException("此项目没有ID为"+problemID+"的问题"));
        }
        return ResultDto.data(detailDto);
    }

    @LoginUserPermission
    @PostMapping("/comment")
    public ResultDto commentProblem(@RequestBody @Valid CommentProblemDto commentProblemDto) {
        commentProblemDto.setCreateUserID(WebUtil.getCurrentUserID());
        problemEditService.commentProblem(commentProblemDto);
        return ResultDto.msg("评论成功");
    }

    /**
     * 给任务指派处理者
     * @param problemEditFormDto 接收数据DTO
     * @return result
     */
    @LoginUserPermission
    @PostMapping("/assign")
    public ResultDto assignProblem(@RequestBody @Valid ProblemEditFormDto problemEditFormDto) {
        if (problemEditFormDto.getUserID() == null) {
            return ResultDto.warning(new Exception("审核用户不能为空"));
        }
        problemEditFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.assignProblem(problemEditFormDto);
        return ResultDto.msg("指派成功");
    }

    /**
     * 修复某个问题
     * @param problemEditFormDto 数据
     * @return result
     */
    @LoginUserPermission
    @PostMapping("/repair")
    public ResultDto repairProblem(@RequestBody @Valid ProblemEditFormDto problemEditFormDto) {
        if (problemEditFormDto.getUserID() == null) {
            return ResultDto.warning(new Exception("审核用户不能为空"));
        }
        problemEditFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.repairProblem(problemEditFormDto);
        return ResultDto.msg("修复成功");
    }

    /**
     * 审核问题是否通过
     * @param problemEditFormDto 问题数据
     * @return result
     */
    @LoginUserPermission
    @PostMapping("/audit")
    public ResultDto auditProblem(@RequestBody @Valid ProblemEditFormDto problemEditFormDto) {
        problemEditFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.auditProblem(problemEditFormDto);
        return ResultDto.msg("审核成功");
    }

    /**
     * 重新打开已经关闭或者解决的问题
     * @param openProblemDto 数据DTO
     * @return result
     */
    @LoginUserPermission
    @PostMapping("/open")
    public ResultDto openProblem(@RequestBody @Valid OpenProblemDto openProblemDto) {
        openProblemDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.openProblem(openProblemDto);
        return ResultDto.msg("打开成功");
    }

    /**
     * 拒绝解决问题
     * @param editFormDto dto
     * @return
     */
    @LoginUserPermission
    @PostMapping("/reject")
    public ResultDto rejectProblem(@RequestBody @Valid ProblemEditFormDto editFormDto) {
        editFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.rejectProblem(editFormDto);
        return ResultDto.msg("拒绝成功");
    }

    /**
     * 关闭问题
     * @param editFormDto
     * @return
     */
    @LoginUserPermission
    @PostMapping("/close")
    public ResultDto closeProblem(@RequestBody @Valid ProblemEditFormDto editFormDto) {
        editFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.closeProblem(editFormDto);
        return ResultDto.msg("问题已经关闭");
    }

    /**
     * 延期问题
     * @param editFormDto 问题DTO
     * @return
     */
    @LoginUserPermission
    @PostMapping("/defer")
    public ResultDto deferProblem(@RequestBody @Valid ProblemEditFormDto editFormDto) {
        editFormDto.setEditUserID(WebUtil.getCurrentUserID());
        problemEditService.deferProblem(editFormDto);
        return ResultDto.msg("延期问题");
    }

    @LoginUserPermission
    @PostMapping("/update")
    public ResultDto updateProblem(@RequestBody ProblemFormDto problemFormDto) {
        problemFormDto.setInUser(WebUtil.getCurrentUserID());
        problemFormDto.setLastEditUser(WebUtil.getCurrentUserID());
        problemService.updateProblem(problemFormDto);
        return ResultDto.msg("保存成功");
    }
}
