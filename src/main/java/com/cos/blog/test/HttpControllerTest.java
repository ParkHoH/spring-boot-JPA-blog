package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller


// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
//		Member m = new Member(1, "ssar", "1234", "email");
		Member m = Member.builder().password("1234").email("email").build();
		System.out.println(TAG + " getter username: " + m.getUsername());
		m.setId(5000);
		System.out.println(TAG + " setter: " +  m.getId());
		return "lombok test 완료";
	}
	
	@GetMapping("/http/get") // Select query(인터넷 브라우저에서만 요청 가능)
	// 한 개씩 parameter 받는 법: public String getTest(@RequestParam int id, @RequestParam String username) {
	public String getTest(Member m) {
		return "get 요청: " + m.getId() + ", " + m.getPassword();
	}
	
	// 한 개씩 parameter 받는 법: @RequestBody 로 annotation을 써줘야 함
	@PostMapping("/http/post") 
	public String postTest(@RequestBody Member m) { // MessageConvertetr (스프링부트 클래스): 자동으로 파싱해서 오브젝트에 해주는 것
		return "post 요청: " + m.getId() + ", " + m.getPassword();
	}
	
	@PutMapping("/http/put") // Update query
	public String putTest() {
		return "put 요청";
	}
	
	@DeleteMapping("/http/delete") // Delete query
	public String deleteTest() {
		return "delete 요청";
	}
}
