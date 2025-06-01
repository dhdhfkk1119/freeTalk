package com.example.freetalk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

// Redis 에 "online-users" 라는 Set 키에 유저 ID 저장/제거
@Service
public class OnlineUserService {
    private static final String ONLINE_USERS_KEY = "online-users";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    // 로그인 성공 시 Redis에 Key: 유저ID 또는 세션ID, Value: 세션 정보 또는 단순 유저명 저장
    public void addUser(String username){
        String key = ONLINE_USERS_KEY + username;
        redisTemplate.opsForSet().add(ONLINE_USERS_KEY,username);
        //redisTemplate.expire(key,600, TimeUnit.SECONDS);

    }

    public void removeUser(String username){
        redisTemplate.opsForSet().remove(ONLINE_USERS_KEY,username);
    }

    public Set<String> getOnlineUsers(){
        return redisTemplate.opsForSet().members(ONLINE_USERS_KEY);
    }

}
