package problem.no.dto.user;

import lombok.Getter;
import lombok.Setter;
import problem.no.enums.user.UserRole;

import java.io.Serializable;

@Getter
@Setter
public class CurrentUserDto implements Serializable {

    private String authUserID;

    private Integer userID;

    private Integer role;

    private Integer status;

    private String userName;
}
