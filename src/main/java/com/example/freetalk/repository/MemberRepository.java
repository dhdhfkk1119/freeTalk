package com.example.freetalk.repository;

import com.example.freetalk.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByUserid(String userid); // 아이디 중복 확인
	
	Optional<Member> findByUserid(String userid);
}
