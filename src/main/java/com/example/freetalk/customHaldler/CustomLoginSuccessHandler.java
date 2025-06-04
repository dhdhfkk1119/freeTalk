package com.example.freetalk.customHaldler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 세션에 사용자 이름 저장
        String username = authentication.getName();
        request.getSession().setAttribute("username", username);
        System.out.println("loginSuccess: " + username);
        System.out.println("세션에 저장된 username: " + request.getSession().getAttribute("username"));


        // 로그인 성공 후 원하는 곳으로 리다이렉트
        response.sendRedirect("/"); // 혹은 원하는 경로
    }
}