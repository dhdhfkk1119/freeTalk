package com.example.freetalk.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // Service 서비스 계층 클래스라고 알려줌
@RequiredArgsConstructor // final , @Nonnull 일경우 자동으로 생성자를 만들어줌
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional // 메서드에서 중간에 에러가 발생하면 데이터베이스 반영된 변경 내용이 자동 취소
	public Member memberRegister(MemberDTO memberdto) {
		String birthDate = memberdto.getYear() + "-" + memberdto.getMonth() + "-" + memberdto.getDay();
		String address = memberdto.getAddressNumber() + "-" + memberdto.getAddress1() + "-" + memberdto.getAddress2();

		Member member = new Member();
		member.setUsername(memberdto.getUsername());
		member.setUserid(memberdto.getUserid());
		member.setPassword(passwordEncoder.encode(memberdto.getPassword()));
		member.setAge(birthDate);
		member.setAddress(address);
		member.setEmail(memberdto.getEmail());
		member.setSex(memberdto.getSex());
		member.setUserAt(LocalDateTime.now());
	
		
		return memberRepository.save(member);
	}
	
}
