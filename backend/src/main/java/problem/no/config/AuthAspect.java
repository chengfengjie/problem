package problem.no.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {

    @Pointcut("execution(* problem.no.controller..*(..))")
    public void requestController() {
    }

    @Before("requestController()")
    public void doBefore(JoinPoint point) {
    }
}
