package com.example.freetalk.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice_alram")
public class NoticeAlram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 보낸 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user")
    private Member sender;

    // 받은 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_user")
    private Member receiver;

    //sender, receiver는 각각 하나의 Member 객체와 연결됨

    private String type;

    private String content;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    
}
