package com.example.freetalk.Security;

import com.example.freetalk.redis.OnlineUserService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class SessionListener {

    private final OnlineUserService onlineUserService;

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                // 세션 종료 시 호출됨
                String username = (String) se.getSession().getAttribute("username");
                if (username != null) {
                    System.out.println("세션 종료됨. 유저 제거: " + username);
                    onlineUserService.removeUser(username); // Redis에서 온라인 유저 제거
                }
            }
        };
    }
}
