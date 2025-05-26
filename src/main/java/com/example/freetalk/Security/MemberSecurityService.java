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
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if("admin".equals(username)) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new User(member.getUserid(), member.getPassword() ,grantedAuthorities);
	}

}
