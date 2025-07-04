package com.example.freetalk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.freetalk.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow , Integer> {
	Optional<Follow> findByFollowUserIdxAndFollowLoginIdx(Long useridx, Long loginidx); // db 에 값을 중복 체크
	List<Follow> findByFollowLoginIdx(Long loginidx); // 내가 팔로우 하고 있음
	List<Follow> findByFollowUserIdx(Long loginidx); // 내가 팔로우 하고 있음
}
