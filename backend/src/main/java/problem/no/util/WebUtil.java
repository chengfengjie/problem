package problem.no.util;

import com.google.common.base.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import problem.no.dto.user.CurrentUserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

    private static final int YEAR_SECOND = 60 * 60 * 24 * 365;
    private static final String AUTH_INFO_SEPARATOR = "_____AUTH_INFO_SEPARATOR__";
    private static final String CURRENT_USER_DTO_KEY = "CURRENT_USER_DTO_KEY";

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static void requestPut(String name, Object value) {
        getRequest().setAttribute(name, value);
    }

    public static Object requestGet(String name) {
        return getRequest().getAttribute(name);
    }

    public static void setCurrentUserDto(CurrentUserDto currentUserDto) {
        requestPut(CURRENT_USER_DTO_KEY, currentUserDto);
    }

    public static CurrentUserDto getCurrentUserDto() {
        return (CurrentUserDto) requestGet(CURRENT_USER_DTO_KEY);
    }

    public static Integer getCurrentUserID() {
        return getCurrentUserDto().getUserID();
    }

    public static String getCurrentUserName() {
        return getCurrentUserDto().getUserName();
    }

    public static void cookieAdd(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        getResponse().addCookie(cookie);
    }

    public static void setCookieAuthUserID(String authUserID) {
        clearCookie();
        cookieAdd(AUTH_INFO_SEPARATOR +  authUserID, authUserID, YEAR_SECOND);
    }

    public static String cookieGet(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getCookieAuthUserID() {
        Cookie[] cookies = getRequest().getCookies();
        String name;
        String value;
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                name = cookie.getName();
                value = cookie.getValue();
                if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(value) && name.startsWith(AUTH_INFO_SEPARATOR)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void clearCookie() {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie aCookie = new Cookie(cookie.getName(), null);
                aCookie.setMaxAge(0);
                aCookie.setPath("/");
                getResponse().addCookie(aCookie);
            }
        }
    }

}

