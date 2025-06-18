package com.example.freetalk.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.FollowRepository;
import com.example.freetalk.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MemberRepository memberRepository;
	private final FollowRepository followRepository;

    @GetMapping("/")
    public String index(Principal principal,Model model){
    	
    	List<Member> members = memberRepository.findAll();
    	
        if (principal != null) {
            String username = principal.getName();
            Member member = memberRepository.findByUserid(username)
                    .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다"));
            System.out.println("Principal.getName(): " + username);
            System.out.println("DB 조회 결과: " + memberRepository.findByUserid(username));
            
            model.addAttribute("member", member);
        } else {
            model.addAttribute("member", null); // 또는 기본 Guest Member를 넘겨도 됨
        }
        model.addAttribute("members",members);
        return "index";
    }

    @GetMapping("/api/check-login")
    public ResponseEntity<?> checkLogin(Principal principal) {
        return (principal != null) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
