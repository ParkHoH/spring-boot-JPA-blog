package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		userRepository.deleteById(id);
		return null;
//		try {
//			userRepository.deleteById(id);
//		} catch (EmptyResultDataAccessException e) { // 귀찮으면 Exception 이라고 하면 됨
//			return ("오류 발생");
//		}
//		return "삭제 되었습니다.";
	}
	
	// Save 함수는 id를 전달하지 않으면 insert를 해주고,
	// id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고,
	// id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해줌.
	@Transactional // 더티 체킹
	@PutMapping("/dummy/user/{id}") // 다른 주소랑 겹쳐도 put이라서 상관없음
	public String updateUser(@PathVariable int id, @RequestBody User userRequest ) { // JSON을 받기 위해서는 @RequestBody 가 필요 , Java Object로 받아주는데 MessageConverter의 역할임 (Jackson 라이브러리 적용)
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 id가 존재하지 않습니다.");
		});
		user.setPassword(userRequest.getPassword());
		user.setEmail(userRequest.getEmail());
		user.setUsername(userRequest.getUsername());
		return "수정 완료";
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건에 데이터를 리턴받기
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser =  userRepository.findAll(pageable);
		List<User> users = pagingUser .getContent();
		return users;
	}
	
	
	
	// {id}: 주소로 파레미터를 전달받을 수 있음
	// user/4 를 찾으면 내가 데이터베이스에서 못 찾아오게 되면 user가 null이 될 거 아니야?
	// 그럼 return null이 리턴되자나.. 그럼 프로그램에 문제가 있지 않겠니?
	// Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
	@GetMapping("/dummy/user/{id}")
	public  User detail(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id:" + id); // 나중에는 스프링 AOP를 이용해 에러 페이지 보여줄 필요가 있음.  
			}
		});
		// 요청: 웹 브라우저
		// user 객체 = 자바 오브젝트
		// 이전 방법 - 변환: 웹 브라우저가 이해할 수 있는 데이터 -> JSON (Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답 시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// 자바 오브젝트를 	JSON으로 변환해서 브라우저에게 던져줌.
		return user;
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다.");
//		});
		
		// 아래 방법은 빈 객체를 넣어주면 null이 아니라 선호하는 방법이 아님
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//		@Override
//		public User get() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		}); // get()은 문제가 없을 거니 그냥 가져와라는 의미인데 위험함.
}
	
	// http://localhost:8000/blog/dummy/join -> 요청
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		
		user.setRole(RoleType.USER); // 하드코딩 시 실수가 생길 가능성이 높음 => enum 생성(데이터의 도메인을 만들 때)
		userRepository.save(user);
		return "회원가입이 안료되었습니다.";
	}
	
	
}
