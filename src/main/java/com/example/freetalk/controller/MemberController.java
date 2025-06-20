package com.example.freetalk.controller;

import com.example.freetalk.dto.MemberDTO;
import com.example.freetalk.repository.MemberRepository;
import com.example.freetalk.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
		try{
			memberService.memberRegister(meber);
			return "redirect:/";

		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	

	
}
