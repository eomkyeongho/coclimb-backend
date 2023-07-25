package swm.s3.coclimb.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import swm.s3.coclimb.api.application.service.UserService;
import swm.s3.coclimb.api.exception.errortype.login.AlreadyLogin;
import swm.s3.coclimb.domain.User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("instagramUserId") != null && session.getAttribute("instagramAccessToken") != null) {
            if(request.getRequestURI().matches("^/login/.*$")) {
                throw new AlreadyLogin();
            }

            return true;
        }

        return autoLogin(request, response, session);
    }

    private boolean autoLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Cookie[] cookies = request.getCookies();
        String instagramAccessToken = null;
        Long instagramUserId = null;

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("instagramAccessToken")) {
                instagramAccessToken = cookie.getValue();
            }
            if(cookie.getName().equals("instagramUserId")) {
                instagramUserId = Long.parseLong(cookie.getValue());
            }
        }

        if(instagramAccessToken == null || instagramUserId == null) {
            return autoLoginFail(request, response);
        }

        User user = userService.findUserByInstagramUserId(instagramUserId).orElse(null);

        if (user == null) {
            return autoLoginFail(request, response);
        }

        if (user.getInstagramAccessToken().equals(instagramAccessToken)) {
            session.setAttribute("instagramUserId", instagramUserId);
            session.setAttribute("instagramAccessToken", instagramAccessToken);

            return autoLoginSuccess(request, response);
        } else {
            return autoLoginFail(request, response);
        }
    }

    private boolean autoLoginFail(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().matches("^/login/.*$")) {
            return true;
        } else {
            response.setStatus(401);
            return false;
        }
    }

    private boolean autoLoginSuccess(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().matches("^/login/.*$")) {
            response.setStatus(200);
            return false;
        }
        return true;
    }
}
