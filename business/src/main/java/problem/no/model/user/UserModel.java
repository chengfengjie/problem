package problem.no.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import problem.no.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserModel extends BaseModel {

    private Integer userID;

    private String email;

    private String cellphone;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String passwordSalt;

    private String userName;

    private Integer status = 1;

    private Integer role;

    private String description;

    private String avatar;

    public UserModel() {}

    public UserModel(String password, String passwordSalt, Date date) {
        this.setPassword(password);
        this.setPasswordSalt(passwordSalt);
        this.setInDate(date);
        this.setLastEditDate(date);
    }

}
