package com.sinfloo.demo.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/forbidden");
    }
	 @Bean
	    public HandlerAdapter handlerAdapter() {
	        return new SimpleControllerHandlerAdapter();
	    }
}
