package problem.no.dto.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by chengfj on 2017/10/23.
 * @author chengfj
 */
@Getter
@Setter
public class CommentProblemDto {

    @NotNull(message = "问题ID不能为空")
    private Integer problemID;

    @NotEmpty(message = "评论内容不能为空")
    @Length(max = 500, message = "评论内容最多500个字")
    private String content;

    private List<String> imageList;

    @JsonIgnore
    private Integer createUserID;

}
