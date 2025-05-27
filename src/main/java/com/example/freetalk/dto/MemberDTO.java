package com.example.freetalk.dto;

import jakarta.validation.constraints.*;
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

	@NotBlank( message = "이메일 입력해주세요")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|co\\.kr)$", message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	
	@NotBlank( message = "성별을 입력해주세요")
	private String sex;
	
	private LocalDateTime userAt;
}
