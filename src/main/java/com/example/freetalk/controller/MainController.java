package com.example.freetalk.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.freetalk.entity.Follow;
import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.FollowRepository;
import com.example.freetalk.repository.MemberRepository;

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

            Map<Long,Member> followingMap = new HashMap<>();
            Map<Long,Member> followedMap = new HashMap<>();
            List<Member> followingMembers = new ArrayList<>();
            List<Member> followedMembers = new ArrayList<>();


            List<Follow> following  = followRepository.findByFollowLoginIdx(member.getIdx()); // 내가 팔로우한 목록

            for(Follow follow : following){
                Member memberList = memberRepository.findByIdx(follow.getFollowUserIdx());
                followingMap.put(follow.getFollowIdx(), memberList);
                followingMembers.add(memberList);
                
            }

            List<Follow> followed  = followRepository.findByFollowUserIdx(member.getIdx()); // 내가 팔로우한 목록
            for(Follow follow : followed){
                Member memberList = memberRepository.findByIdx(follow.getFollowLoginIdx());
                followedMap.put(follow.getFollowIdx(), memberList);
                followedMembers.add(memberList);
                
            }

            model.addAttribute("member", member);
            model.addAttribute("followingList", followingMembers);
            model.addAttribute("followedList", followedMembers);

        } else {
            model.addAttribute("member", null); // 또는 기본 Guest Member를 넘겨도 됨
        }
        model.addAttribute("members",members);
        return "index";
    }

    // 로그인 체크 하기 
    @GetMapping("/api/check-login")
    public ResponseEntity<?> checkLogin(Principal principal) {
        return (principal != null) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
