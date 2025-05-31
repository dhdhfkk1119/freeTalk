package com.example.freetalk.redis;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.esotericsoftware.minlog.Log;
import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OnlineUserController {

	private final MemberRepository memberRepository;
	
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private OnlineUserService onlineUserService;

    @MessageMapping("/user/connected")
    public void handleUserConnected(Principal principal){
    
    	
        String username = principal.getName();
        Optional<Member> member = memberRepository.findByUserid(username);
        String userid = member.get().getUserid();
        
        System.out.println("WebSocket 연결 유저 ID: {}" + member.get().getUserid());
        onlineUserService.addUser(username);
        broadcastUserList();
    }


    private void broadcastUserList() {
        Set<String> users = onlineUserService.getOnlineUsers();
        messagingTemplate.convertAndSend("/topic/online-users",users);
    }

}
