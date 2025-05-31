package com.example.freetalk.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	// spring.redis.password가 redis1234로 설정되어 있으니, Redisson도 동일하게 맞춰주기
	// redis 실행하기 
    @Bean	
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
        	.setAddress("redis://localhost:6379")
        	.setPassword("redis1234");
        return Redisson.create(config);
    }
}
