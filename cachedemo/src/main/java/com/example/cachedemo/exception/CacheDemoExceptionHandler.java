package com.example.cachedemo.exception;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;

@ControllerAdvice
public class CacheDemoExceptionHandler implements AsyncUncaughtExceptionHandler {

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Object notSupportMethodException(HttpRequestMethodNotSupportedException ex, WebRequest request){
        System.out.println("HttpRequestMethodNotSupportedException msg:" + ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(value = Throwable.class)
    public Object commonException(Throwable ex, WebRequest request){
        System.out.println("Throwable msg:" + ex.getMessage());
        return ex.getMessage();
    }
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("exception " + ex.getMessage());
        System.out.println("method :" + method.getName());
    }
}
