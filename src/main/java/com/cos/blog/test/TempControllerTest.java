package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temp home");
		// Controller annotation 은 파일을 return 해줌
		// 파일리턴 기본 경로: src/main/resources/static
		// 리턴명: /home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJSP() {
		System.out.println("temp jsp");
		// Controller annotation 은 파일을 return 해줌
		// 파일리턴 기본 경로: src/main/resources/static
		// 리턴명: /home.html
		return "test";
	}
}
