package com.example.freetalk.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // 데이터베이스 테이블과 자동매핑
@Getter // 자동으로 getter 을 만들어줌
@Setter // 자동으로 setter 을 만들어줌
@NoArgsConstructor // 파라미터가 없는 기본 생서자를 자동으로 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 자동으로 생성

public class Follow {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 PK  
    private int followIdx;
	
    private Long followLoginIdx;
    private Long followUserIdx;
    private LocalDateTime followedAt;
}
