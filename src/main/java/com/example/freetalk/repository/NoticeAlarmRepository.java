package com.example.freetalk.repository;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.freetalk.entity.NoticeAlram;

@Repository
public interface NoticeAlarmRepository extends JpaRepository<NoticeAlram,Long>{
    List<NoticeAlram> findByReceiverAndIsReadFalse(com.example.freetalk.entity.Member member); // 안 읽은 알림
    List<NoticeAlram> findByReceiver(Member receiver); // 전체 알림
} 
