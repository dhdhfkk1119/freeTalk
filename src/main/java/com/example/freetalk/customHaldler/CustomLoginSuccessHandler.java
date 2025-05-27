package com.example.freetalk.customHaldler;

import com.example.freetalk.redis.RedissonConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RedissonConfig redissonConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String username = authentication.getName();

    }

}
