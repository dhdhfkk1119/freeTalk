package com.example.freetalk.Security;

import com.example.freetalk.entity.Member;
import com.example.freetalk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService{
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> _member = this.memberRepository.findByUserid(username);
		if(_member.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수없습니다");
		}
		Member member = _member.get();
		// grantedAuthorities 유저가 갖는 권한을 저장하는 리스트
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if("admin".equals(username)) {
			// SimpleGrantedAuthority : GrantedAuthority 인터페이스의 구현체로 권한을 문자열로 표현 할수 있게해줌
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		// 로그인 인증이 되어 값이 있을경우 return 해준다
		return new User(member.getUserid(), member.getPassword() ,grantedAuthorities);
	}

}
