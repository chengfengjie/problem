package problem.no.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import problem.no.dto.user.CurrentUserDto;
import problem.no.enums.user.UserRole;
import problem.no.exception.PNException;
import problem.no.interpretation.AdminPermission;
import problem.no.interpretation.LoginUserPermission;
import problem.no.manager.UserCacheManager;
import problem.no.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserCacheManager userCacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod =(HandlerMethod) handler;
        CurrentUserDto currentUserDto = userCacheManager.getCurrentUserDto(WebUtil.getCookieAuthUserID());

        if (currentUserDto != null) {
            WebUtil.setCurrentUserDto(currentUserDto);
        }
        AdminPermission adminPermission = handlerMethod.getMethod().getAnnotation(AdminPermission.class);
        LoginUserPermission loginUserPermission = handlerMethod.getMethod().getAnnotation(LoginUserPermission.class);
        if (loginUserPermission != null && currentUserDto == null) {
            throw PNException.ForbiddenException();
        }
        if (adminPermission != null) {
            if (currentUserDto == null) {
                throw new PNException("请登录");
            }
            if (!currentUserDto.getRole().equals(UserRole.SUPER_ADMIN.getCode())) {
                throw new PNException("需要管理员权限访问");
            }
        }
        return super.preHandle(request, response, handler);
    }
}
