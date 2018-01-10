package problem.no.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String cellphone;

    @NotBlank(message = "请填写用户名称")
    private String userName;

    private String avatar;

    private String description;

    private String password;
}
