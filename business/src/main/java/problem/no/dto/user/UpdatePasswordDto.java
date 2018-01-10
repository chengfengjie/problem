package problem.no.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author  by chengfj on 2017/11/7.
 */
@Getter
@Setter
public class UpdatePasswordDto {
    @NotBlank(message = "原始密码不能为空")
    private String originalPassword;
    @NotBlank(message = "密码不能为空")
    private String password;
    @JsonIgnore
    private Integer userID;
}
