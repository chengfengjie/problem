package problem.no.dto.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberDto {

    private Integer id;

    private Integer userID;

    private Integer projectID;

    private String displayName;

    private Boolean isAdmin;

    private Boolean isOwner;

    private String email;

    private String userName;

    private String cellphone;
}
