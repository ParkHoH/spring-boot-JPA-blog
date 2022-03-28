package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // insert 시에 null인 필드를 제외시켜줌
// ORM -> Java(+다른 언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {
	
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 100개까지 주는 이유는 hash로 변경해서 암호화하기 위해
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50)
	private String email; // 이메일
	
	// @ColumnDefault("'user'")
	@Enumerated(EnumType.STRING) // DB는 RoleType이라는 게 없기 때문에 해당 annotaion 추가
	private RoleType role; // 정확하게 하기 위해서는 Enum을 쓰는 게 좋음. Enum은 도메인(범위)를 정할 수 있음 like admin, user, manager
	
	@CreationTimestamp // 시간이 자동 입력
	private Timestamp createDate;	
	
}
