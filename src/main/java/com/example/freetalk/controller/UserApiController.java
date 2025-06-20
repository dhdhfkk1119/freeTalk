package com.example.freetalk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.freetalk.dto.NoticeAlramDTO;
import com.example.freetalk.entity.Member;
import com.example.freetalk.entity.NoticeAlram;
import com.example.freetalk.repository.MemberRepository;
import com.example.freetalk.repository.NoticeAlarmRepository;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController // HTTP 데이터 전송용
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final MemberRepository memberRepository;
    private final NoticeAlarmRepository noticeAlarmRepository;

    // 팔로우 한 유저의 ID 값을 받아온다 HTTP
    @GetMapping("/current-userid")
    public ResponseEntity<Map<String, String>> getCurrentUserid(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userid = principal.getName();
        return ResponseEntity.ok(Map.of("userid", userid)); // http 로 현재 로그인한 유저의 아이디 값을 보낸다
    }

    // 로그인 시 or 알림 아이콘 클릭 시, 안 읽은 알림 조회
    @GetMapping("/notifications/unread")
    public ResponseEntity<List<NoticeAlramDTO>> getFollowAlramIsRead(Principal principal) {

        String userid = principal.getName();
        Member member = memberRepository.findByUserid(userid)
            .orElseThrow(() -> new RuntimeException("사용자 없음"));
        
        List<NoticeAlram> noticeAlrams = noticeAlarmRepository.findByReceiverAndIsReadFalse(member);
        List<NoticeAlramDTO> dtos = noticeAlrams.stream()
                .map(NoticeAlramDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);

    }

    // 알림 읽음 처리 API
    @PutMapping("/notifications/read/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id, Principal principal) {
        Member member = memberRepository.findByUserid(principal.getName())
            .orElseThrow(() -> new RuntimeException("사용자 없음"));

        NoticeAlram noti = noticeAlarmRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("알림 없음"));

        if(!noti.getReceiver().equals(member)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        noti.setRead(true);
        noticeAlarmRepository.save(noti);
        return ResponseEntity.ok().build();
    }

}
