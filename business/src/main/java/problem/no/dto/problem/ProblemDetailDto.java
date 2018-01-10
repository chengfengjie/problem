package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.problem.ProblemEditModel;
import problem.no.model.problem.ProblemImageModel;

import java.util.List;

/**
 * Created by chengfj on 2017/10/20.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemDetailDto extends ProblemListDto {

    List<ProblemEditResultDto> editInfoList;

    List<ProblemImageModel> imageList;

    /**
     * 是否具有评论权限
     */
    private Boolean hasCommentPerssion;
    /**
     * 是否具有指派权限
     */
    private Boolean hasAssignPerssion;
    /**
     * 是否具有修复权限
     */
    private Boolean hasRepairPerssion;
    /**
     * 审核通过权限
     */
    private Boolean hasAuditPassPerssion;
    /**
     * 审核不通过权限
     */
    private Boolean hasAuditNotPassPerssion;
    /**
     * 再打开权限
     */
    private Boolean hasOpenProblemPerssion;
    /**
     * 拒绝权限
     */
    private Boolean hasRejectPerssion;
    /**
     * 关闭问题权限
     */
    private Boolean hasCloseProblemPerssion;
    /**
     * 延期问题权限
     */
    private Boolean hasDeferProblemPerssion;
}
