package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.MemberDTO;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller // Controller 라는 클래스 계층이라고 알려줌 
@RequiredArgsConstructor // 자동 생성자 final , NonNull 일겨우에
public class MemberController {

    private final MemberRepository memberRepository;
	private final MemberService memberService;

	// 회원가입 페이지 
	@GetMapping("/register")
	public String register(Model model) {
		
		model.addAttribute("member",new MemberDTO()); // DTO에 빈객체를 만들어준다 
		return "register";
	}
	
	// 로그인 페이지 
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	// 아이디 중복 체크 
	@GetMapping("/isCheck")
	@ResponseBody
	public Map<String, Boolean> isCheck(@RequestParam("userid") String userid) {
	    boolean available = !memberRepository.existsByUserid(userid);
	    Map<String, Boolean> result = new HashMap<>();
	    result.put("available", available);
	    return result;
	}
	
	
	// 회원가입 값 맵핑 ORM
	@PostMapping("/register")
	public String RegisterPage(@Valid @ModelAttribute("member") MemberDTO meber,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		if(!meber.getPassword().equals(meber.getRepassword())) {
			bindingResult.rejectValue("repassword", "passwordInCorrect","2개의 비밀번호가 일치하지 않습니다");
			return "register";
		}
		memberService.memberRegister(meber);
		
		return "/index";
	}
	

	
}
