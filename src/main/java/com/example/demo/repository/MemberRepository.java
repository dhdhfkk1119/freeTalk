package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByUserid(String userid); // 아이디 중복 확인
	
	Optional<Member> findByUserid(String userid);
}
