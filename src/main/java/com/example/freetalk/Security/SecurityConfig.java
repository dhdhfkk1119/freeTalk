package com.example.freetalk.Security;

import com.example.freetalk.redis.OnlineUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		//.csrf().disable() 
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/register", "/login", "/","/css/**","/images/**","/isCheck","/**").permitAll()
			.anyRequest().authenticated())
		.formLogin((formLogin) -> formLogin
				.loginPage("/login")
				.defaultSuccessUrl("/"))
		.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessHandler((request, response, authentication) -> {
					if (authentication != null) {
						String username = authentication.getName();
						onlineUserService.removeUser(username); // Redis에서 제거
					}
					response.sendRedirect("/");
				})
				.invalidateHttpSession(true))
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
