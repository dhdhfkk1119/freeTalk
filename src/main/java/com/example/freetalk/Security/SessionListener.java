package com.example.freetalk.Security;

import com.example.freetalk.redis.OnlineUserService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {
    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 세션 종료 시 호출됨
        String username = (String) se.getSession().getAttribute("username");
        if (username != null) {
            onlineUserService.removeUser(username); // Redis에서 온라인 유저 제거
        }
    }
}
