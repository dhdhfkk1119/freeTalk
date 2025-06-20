package com.example.freetalk.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.freetalk.dto.NoticeAlramDTO;
import com.example.freetalk.entity.Member;
import com.example.freetalk.entity.NoticeAlram;
import com.example.freetalk.repository.MemberRepository;
import com.example.freetalk.repository.NoticeAlarmRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AlramController {
    
    private final NoticeAlarmRepository noticeAlarmRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/alram")
    private String alram(Principal principal,Model model){
        
        String userid = principal.getName();
        Member member = memberRepository.findByUserid(userid)
            .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수없습니다"));
        
        // 전체 알람 조회
        List<NoticeAlram> noticeAlrams = noticeAlarmRepository.findByReceiver(member);       
        // DTO로 변환
        List<NoticeAlramDTO> noticeAlramDTOs = noticeAlrams.stream()
            .map(NoticeAlramDTO::fromEntity)
            .collect(Collectors.toList());
        
        model.addAttribute("noticeAlrams", noticeAlramDTOs);

        return "alram";
    }
}
