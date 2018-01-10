package problem.no.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PNException extends RuntimeException {
    private boolean forbidden = false;
    public PNException(String msg) {
        super(msg);
    }
    public static PNException ForbiddenException() {
        PNException e = new PNException("授权失败");
        e.forbidden = true;
        return e;
    }
}
