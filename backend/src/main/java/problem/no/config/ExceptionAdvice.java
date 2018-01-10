package problem.no.config;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import problem.no.dto.ResultDto;
import problem.no.exception.PNException;

import java.lang.reflect.Field;
import java.util.List;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    @ResponseBody
    public ResultDto handleException(Exception e) {
        if (e instanceof PNException) {
            PNException pnException = (PNException) e;
            if (pnException.isForbidden()) {
                return ResultDto.forbidden(e);
            } else {
                return ResultDto.warning(e);
            }
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return ResultDto.warning(new Exception(parserDefaultErrorMessage(exception)));
        }
        e.printStackTrace();
        return ResultDto.error(e);
    }

    private String parserDefaultErrorMessage(MethodArgumentNotValidException validException) {
        try {
            Field field = validException.getClass().getDeclaredField("bindingResult");
            field.setAccessible(true);
            BeanPropertyBindingResult bindingResult =(BeanPropertyBindingResult) field.get(validException);
            List<FieldError> list = bindingResult.getFieldErrors();
            String errorMessage = "";
            for (FieldError error : list) {
                errorMessage = errorMessage + error.getDefaultMessage();
            }
            if (errorMessage.isEmpty()) {
                return "参数未知错误";
            } else {
                return errorMessage;
            }
        } catch (Exception em) {
            return em.getMessage();
        }
    }
}
