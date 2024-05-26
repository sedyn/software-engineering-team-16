package kr.ac.cau.issue.controller.resolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.cau.issue.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@RequiredArgsConstructor
public class SessionResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserSession.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        try {
            Cookie cookie = WebUtils.getCookie((HttpServletRequest) webRequest.getNativeRequest(), "session");
            return sessionService.getUserFromSession(UUID.fromString(cookie.getValue())).orElseThrow();
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
            response.sendRedirect("/login");
            return null;
        }
    }
}
