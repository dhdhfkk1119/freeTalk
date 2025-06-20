package com.example.freetalk.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.freetalk.entity.Member;
import com.example.freetalk.entity.NoticeAlram;
import com.example.freetalk.redis.OnlineUserService;
import com.example.freetalk.repository.MemberRepository;
import com.example.freetalk.repository.NoticeAlarmRepository;
import com.example.freetalk.service.FollowService;
import com.mysql.cj.protocol.x.Notice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FollowController {

	private final OnlineUserService onlineUserService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final FollowService followService;
	private final MemberRepository memberRepository;
	private final NoticeAlarmRepository noticeAlarmRepository;

	@GetMapping("/follow/checked/btn/{userIdx}")
	public ResponseEntity<Map<String, Object>> isFollowedCheck(
			Principal principal,
			@PathVariable(name = "userIdx") Long userIdx) {

		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("status", "error", "message", "로그인이 필요합니다"));
		}

		Map<String, Object> response = new HashMap<>();

		String userid = principal.getName();
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다"));
		Long loginidx = member.getIdx(); // 현재 로그인한 유저의 아이디 값을 받음

		boolean isfollowing = followService.isFollowCheck(loginidx, userIdx); // 상대가 나를 팔로우 하고 있을 때
		boolean isfollowed = followService.isFollowCheck(userIdx, loginidx); // 내가 상대를 팔로우 하고 있을때

		if (isfollowing) {
			response.put("status", "following"); // 내가 팔로우함
		} else if (isfollowed) {
			response.put("status", "followed"); // 나를 팔로우함
		} else {
			response.put("status", "notfollowed");
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/follow/checked/btn/{userIdx}")
	public ResponseEntity<Map<String, Object>> ischeckBtn(
			Principal principal,
			@PathVariable(name = "userIdx") Long userIdx) {

		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("status", "error", "message", "로그인이 필요합니다"));
		}

		Map<String, Object> response = new HashMap<>();

		String userid = principal.getName();
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다"));
		Long loginidx = member.getIdx(); // 현재 로그인한 유저의 아이디 값을 받음

		boolean isfollowing = followService.getAlreadyFollowingUserIds(loginidx, userIdx); // 내가 상대를 팔로우 하고 있음

		if (isfollowing) {
			response.put("status", "following"); // 내가 팔로우함

			// 내가 상대를 팔로우 할 경우에만 메세지를 보낸다
			// ✅ 여기에서 알림 보내기
			Member targetMember = memberRepository.findById(userIdx)
					.orElseThrow(() -> new RuntimeException("팔로우 대상 유저를 찾을 수 없습니다"));
			String targetUserid = targetMember.getUserid(); // 알림 보낼 대상의 userid

			// 온라인 상태일 경우에만 WebSocket 알림 전송
			if (onlineUserService.getOnlineUsers().contains(targetUserid)) {
				simpMessagingTemplate.convertAndSend("/topic/notify/" + targetUserid,
						Map.of(
								"type", "follow",
								"from", member.getUserid(),
								"content", member.getUsername() + "님이 당신을 팔로우했습니다!"));
				
				// DB에 저장(사용자의 팔로우 알람에 대한 기능을)
				NoticeAlram noticeAlram = new NoticeAlram();
				noticeAlram.setSender(member);
				noticeAlram.setReceiver(targetMember);
				noticeAlram.setType("follow");
				noticeAlram.setContent(targetUserid + "님이 당신을 팔로우 했습니다");
				noticeAlarmRepository.save(noticeAlram);

			}

		} else {
			response.put("status", "removefollow"); // 나를 팔로우함
		}

		return ResponseEntity.ok(response);
	}

	// 로그인시 or 알림 아이콘 클릭시 안 읽은 알림 조회

}
