package com.example.freetalk.redis;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

// Redis 에 "online-users" 라는 Set 키에 유저 ID 저장/제거
@Service
@RequiredArgsConstructor
public class OnlineUserService {

    private static final String ONLINE_USERS_KEY = "online-users";

    private final RedissonClient redissonClient;


    public void addUser(String username){
        RSet<String> onlineUsers = redissonClient.getSet(ONLINE_USERS_KEY);
        onlineUsers.add(username);
    }

    public void removeUser(String username){
        RSet<String> onlineUsers = redissonClient.getSet(ONLINE_USERS_KEY);
        onlineUsers.remove(username);
    }

    public Set<String> getOnlineUsers(){
        RSet<String> onlineUsers = redissonClient.getSet(ONLINE_USERS_KEY);
        return new HashSet<>(onlineUsers.readAll());
    }

    
}

