package com.example.freetalk.customHaldler;

import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.Optional;

@Component
@ControllerAdvice
@RequiredArgsConstructor
@Transactional
public class MemberHandler {

    private final MemberRepository memberRepository;

    // 현재 로그인한 유저의 정보를 가져옴 
    @ModelAttribute
    public void sessionMemberInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증된 유저의 정보를 가져온다

        // 인증된 객체가 있을 경우 가져온다 ( if 문을 안적어주면 인증된 객체가 없어도 가져올려고 해서 오류가 발생 )
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            // User 객체안에 저장된 인증받은 유저를 확인 해서 가져오고
            User user = (User) authentication.getPrincipal();
            // 인증 받은 유저의 정보를 가져온다 username 유저의 아이디 값
            String userId = user.getUsername();
            // 가져온 값을 member 데이터베이스에서 찾는다
            Optional<Member> principal = memberRepository.findByUserid(userId);

            // 해당 유저가 있을 경우 model로 해당 유저를 html 객체로 반환해준다
            if (principal.isPresent()) {
                Member member = principal.get();
                model.addAttribute("username", member.getUsername());
            } else {
                model.addAttribute("username", "");
            }

            // 유저 관리 단계를 가져옴
            // 인증 객체에서 권한 목록 가져오기 (GrantedAuthority) 에 저장되어있는
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // 권한 이름(ROLE_*)을 스트림으로 처리해서 첫 번째 권한만 가져오기
            // 가장 첫 번째 하나만 가져오고 , 없다면 기본값 ROLE_USER 을 사용한다 기본값(ROLE_ADMIN)
            String roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER");

            model.addAttribute("roles", roles);
        }


    }
}
