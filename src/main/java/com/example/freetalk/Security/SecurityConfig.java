package com.example.freetalk.Security;

import com.example.freetalk.customHaldler.CustomLoginSuccessHandler;
import com.example.freetalk.redis.OnlineUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity 
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final OnlineUserService onlineUserService;

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/register", "/login", "/","/css/**","/images/**","/isCheck","/**").permitAll()
			.requestMatchers("/ws/**").authenticated() // Websocket 엔드포인트는 로그인 필요
			.anyRequest()
				.authenticated())
		.formLogin((formLogin) -> formLogin
				.loginPage("/login")
				.successHandler(customLoginSuccessHandler))
		.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessHandler((request, response, authentication) -> {
					if (authentication != null) {
						String username = authentication.getName();
						onlineUserService.removeUser(username); 
					}
					response.sendRedirect("/");
				})
				.invalidateHttpSession(true))
		.sessionManagement(session -> session
				.maximumSessions(1)// 동시 로그인 허용수
				.maxSessionsPreventsLogin(false) // 기존 세견 만료 허용
				.expiredUrl("/")
		)
		.sessionManagement(session -> session
				.invalidSessionUrl("/")
		)
		;
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
