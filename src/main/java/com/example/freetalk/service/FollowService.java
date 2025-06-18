package com.example.freetalk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.freetalk.entity.Follow;
import com.example.freetalk.repository.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final FollowRepository followRepository;
	
	public boolean isFollowCheck(Long useridx , Long loginidx) {
		Optional<Follow> existingFollowed = followRepository.findByFollowUserIdxAndFollowLoginIdx(loginidx, useridx); // 팔로우 했는지 체크 
		return existingFollowed.isPresent();
	}
	
	// 팔로우에 대한 데이터 넣는 기능 (팔로잉 , 맞팔로우)
	@Transactional
	public boolean getAlreadyFollowingUserIds(Long loginidx, Long useridx) {
	    Optional<Follow> follows = followRepository.findByFollowUserIdxAndFollowLoginIdx(useridx , loginidx);
	    
		if(follows.isPresent()){
			return false;
		}

	    Follow follow = new Follow();
	    follow.setFollowLoginIdx(loginidx);
	    follow.setFollowUserIdx(useridx);
	    follow.setFollowedAt(LocalDateTime.now());
	    followRepository.save(follow);
	    
	    // 팔로우 한 대상이 있으면 true를 반납한다
	    return true;
	}
	
	
	// 팔로우에 대한 데이터를 HTML로 보내주는 기능(팔로우 체크) 
}
