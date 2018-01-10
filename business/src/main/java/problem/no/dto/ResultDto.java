package problem.no.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

@Getter
@Setter
public class ResultDto<T> implements Serializable {

    public static final Integer OK = 0;
    public static final Integer ERROR = 1;
    public static final Integer WARNING = 2;
    public static final Integer FORBIDDEN = 3;

    private Integer code;

    private String message;

    private T data;

    private String errorMessage;

    private String stackTrace;

    public ResultDto() {}

    public ResultDto(Integer code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ResultDto(String message, T data) {
        this.code = ResultDto.OK;
        this.message = message;
        this.data = data;
    }

    public static ResultDto data(Object data) {
        return new ResultDto<>(0, "", data);
    }

    public static ResultDto ok(String message, Object data) {
        return new ResultDto<>(message, data);
    }

    public static ResultDto msg(String message) {
        return ResultDto.ok(message, null);
    }

    public static ResultDto ok() {
        return ResultDto.msg("ok");
    }

    public static ResultDto error(Exception e) {
        ResultDto dto = new ResultDto();
        dto.setCode(ResultDto.ERROR);
        if (e != null) {
            dto.setErrorMessage(e.getMessage());
            dto.setStackTrace(exceptionStacktraceToString(e));
        }
        return dto;
    }

    public static ResultDto warning(Exception e) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ResultDto.WARNING);
        if (e != null) {
            resultDto.setErrorMessage(e.getMessage());
        }
        return resultDto;
    }

    public static ResultDto forbidden(Exception e) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ResultDto.FORBIDDEN);
        if (e != null) {
            resultDto.setErrorMessage(e.getMessage());
        }
        return resultDto;
    }

    public static String exceptionStacktraceToString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }
}
