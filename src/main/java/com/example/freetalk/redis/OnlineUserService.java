package com.example.freetalk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
// Redis 에 "online-users" 라는 Set 키에 유저 ID 저장/제거
@Service
public class OnlineUserService {
    private static final String ONLINE_USERS_KEY = "online-users";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void addUser(String username){
        redisTemplate.opsForSet().add(ONLINE_USERS_KEY,username);
        System.out.println("온라인 유저 추가됨: " + username);
    }

    public void removeUser(String username){
        redisTemplate.opsForSet().remove(ONLINE_USERS_KEY,username);
    }

    public Set<String> getOnlineUsers(){
        return redisTemplate.opsForSet().members(ONLINE_USERS_KEY);
    }

}
