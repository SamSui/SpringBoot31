package com.example.cachedemo.resolver;

import com.example.cachedemo.command.UserCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserCommandResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserCommand.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String username = request.getParameter("username");
        String userID = request.getParameter("userID");
        String userAge = request.getParameter("userAge");

        UserCommand command = new UserCommand();
        command.setUserName(username+"_resolver");
        command.setUserID(userID+"_resolver");
        command.setUserAge(Integer.parseInt(userAge) + 10);
        return command;
    }
}
