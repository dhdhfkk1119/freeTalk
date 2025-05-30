package com.example.freetalk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Set;

@Controller
public class OnlineUserController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private OnlineUserService onlineUserService;

    @MessageMapping("/user/connected")
    public void handleUserConnected(Principal principal){
        String username = principal.getName();
        onlineUserService.addUser(username);
        broadcastUserList();
    }

    private void broadcastUserList() {
        Set<String> users = onlineUserService.getOnlineUsers();
        messagingTemplate.convertAndSend("/topic/online-users",users);
    }

}
