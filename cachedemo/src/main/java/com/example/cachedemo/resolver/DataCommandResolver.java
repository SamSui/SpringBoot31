package com.example.cachedemo.resolver;

import com.example.cachedemo.command.DataCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DataCommandResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return DataCommand.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String dataName = request.getParameter("dataName");
        String ipAddress = request.getParameter("ipAddress");
        DataCommand command = new DataCommand();
        command.setDataName(dataName+"_resolver");
        command.setIpAddress(ipAddress+"_resolver");
        return command;
    }
}
