package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.problem.ProblemEditImageModel;
import problem.no.model.problem.ProblemEditModel;

import java.util.List;

/**
 * Created by chengfj on 2017/10/23.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemEditResultDto extends ProblemEditModel {
    private String inUserName;
    private List<ProblemEditImageModel> imageList;
    private String solveUserName;

    private String rejectTypeName;

    public String getRejectTypeName() {
        if (this.getRejectType() == null) {
            return null;
        }
        switch (this.getRejectType()) {
            case 1: return "不是错误";
            case 2: return "重复问题";
            case 3: return "延期解决";
            case 4: return "设计如此";
            case 5: return "不能复现";
            case 6: return "不同意意见";
            case 7: return "忽略";
            default: return null;
        }
    }
}
