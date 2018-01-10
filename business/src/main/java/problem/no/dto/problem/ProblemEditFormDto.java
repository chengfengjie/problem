package problem.no.dto.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by chengfj on 2017/10/24.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemEditFormDto {

    private Integer userID;

    @NotNull(message = "问题ID不能为空")
    private Integer problemID;

    @Length(max = 500, message = "评论内容最多500个字")
    private String content;

    private List<String> imageList;

    @JsonIgnore
    private Integer editUserID;

    /**
     * 是否审核通过
     */
    private Boolean auditPass;
    private String auditVersionName;

    /**
     * 拒绝原因
     */
    private Integer rejectType;

}
