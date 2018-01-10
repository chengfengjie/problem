package problem.no.dto.problem;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by chengfj on 2017/10/25.
 * @author chengfj
 */
@Getter
@Setter
public class OpenProblemDto {

    private Integer problemID;

    private String versionName;

    private Integer solveUserID;

    private Date planBeginDate;

    private Date planFinishDate;

    private Integer priority;

    private String content;

    private List<String> imageList;

    private Integer editUserID;

}
