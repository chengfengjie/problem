package problem.no.model.user.query;

import lombok.Getter;
import lombok.Setter;
import problem.no.model.BaseQueryFilter;

@Getter
@Setter
public class UserQueryFilter extends BaseQueryFilter {
    private String email;
    private String userName;
    private String cellphone;
}
