package problem.no.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseModel implements Serializable {

    private Integer inUser;

    private Date inDate = new Date();

    private Integer lastEditUser;

    private Date lastEditDate = new Date();

    private Boolean isActive = true;
}
