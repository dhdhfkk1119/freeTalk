package com.example.freetalk.HandlerModelAttribute;

import com.example.freetalk.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
@RequiredArgsConstructor
@Transactional
public class MemberHandler {

    private final MemberRepository memberRepository;
    
    // 현재 로그인한 유저의 정보를 가져옴 
    @ModelAttribute
    public void sessionMemberInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증된 유저의 정보를 가져온다
    }
}
