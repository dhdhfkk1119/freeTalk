package com.example.freetalk.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	@NotBlank( message = "이름을 입력해주세여")
	private String username;
	
	@NotBlank( message = "아이디를 입력해주세여")
	private String userid;
	
	@NotBlank( message = "비밀번호를 입력해주세여")
	@Size(min = 6, max = 13, message = "비밀번호는 6 ~ 13자 사이여야 합니다!")
	private String password;
	
	@NotBlank( message = "재 비밀번호를 입력해주세여")
	@Size(min = 6, max = 13, message = "비밀번호는 6 ~ 13자 사이여야 합니다!")
	private String repassword;
	
	@NotNull( message = "년도를 선택해주세요")
	private Integer year;
	@NotNull( message = "월을 선택해주세요")
	private Integer month;
	@NotNull( message = "일을 선택해주세요")
	private Integer day;
	
	@NotBlank( message = "지번을 입력해주세요")
	private String addressNumber;
	
	@NotBlank( message = "주소를 입력해주세요")
	private String address1;
	
	@NotBlank( message = "상세 주소를 입력해주세요")
	private String address2;

	
	@Email( message = "올바른 이메일 형식이 아닙니다")
	@NotBlank( message = "이메일 입력해주세요")
	private String email;
	
	@NotBlank( message = "성별을 입력해주세요")
	private String sex;
	
	private LocalDateTime userAt;
}
