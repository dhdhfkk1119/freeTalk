package com.example.freetalk.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.MemberRepository;
import com.example.freetalk.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class FollowController {
	
	private final FollowService followService;
	private final MemberRepository memberRepository;
	
	@GetMapping("/follow/checked/btn/{userIdx}")
	public ResponseEntity<Map<String,Object>> isFollowedCheck(
			Principal principal,
			@PathVariable Long userIdx){
		
		if(principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "로그인이 필요합니다"));
		}
		
		Map<String,Object> response = new HashMap<>();

		String userid = principal.getName();
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다"));
		Long loginidx = member.getIdx(); // 현재 로그인한 유저의 아이디 값을 받음
		
		boolean isfollowed = followService.getAlreadyFollowingUserIds(userIdx,loginidx); // 상대가 나를 팔로우 하고 있을 때
		boolean isfollowing = followService.getAlreadyFollowingUserIds(loginidx,userIdx); // 내가 상대를 팔로우 하고 있을때
		
		if(isfollowed){
			response.put("status","followed"); // 나를 팔로우함
		} else if(isfollowing){
			response.put("status","following"); // 내가 팔로우함
		}
		
		
		return ResponseEntity.ok(response);
	}

	@PostMapping("/follow/checked/btn/{userIdx}")
	public ResponseEntity<Map<String,Object>> ischeckBtn(
			Principal principal,
			@PathVariable Long userIdx){
		
		if(principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "로그인이 필요합니다"));
		}
		
		Map<String,Object> response = new HashMap<>();

		String userid = principal.getName();
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다"));
		Long loginidx = member.getIdx(); // 현재 로그인한 유저의 아이디 값을 받음
		
		boolean isfollowed = followService.getAlreadyFollowingUserIds(userIdx,loginidx); // 상대가 나를 팔로우 하고 있을 때
		boolean isfollowing = followService.getAlreadyFollowingUserIds(loginidx,userIdx); // 내가 상대를 팔로우 하고 있을때
		
		if(isfollowed){
			response.put("status","followed"); // 나를 팔로우함
		} else if(isfollowing){
			response.put("status","following"); // 내가 팔로우함
		}
		
		
		return ResponseEntity.ok(response);
	}
	
	
}
