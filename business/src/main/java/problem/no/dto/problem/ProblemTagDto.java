package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by chengfj on 2017/10/30.
 * @author chengfj
 */
@Getter
@Setter
public class ProblemTagDto {
    private Integer tagID;
    private Integer problemTagID;
    private String tagName;
    private String tagColor;
}
