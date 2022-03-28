package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	 
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리: <html> 태그가 섞여서 디자인 됨 -> 글자 용량이 커짐
	
	@ColumnDefault("0")
	private int count; // 조회수
	
	// DB는 오브젝트를 저장할 수 없다. 그래서 FK를 사용함
	// 자바는 오브젝트를 저장할 수 있다.
	@ManyToOne // Many = Board, One = user <- 한 명의 유저는 여러 개의 게시글을 쓸 수 있음
	@JoinColumn(name = "userId")
	private User user;  // PK를 따라감
	
	// OneToMany의 기본 전략은 eager 전략이 아님. 왜냐하면 엄청 많을 수 있기 때문에
	// Fetch type이 eager라는 의미는 한 번에 다 가져온다는 의미이고, Lazy는 '펼치기 기능'과 같이 나눠서 가져올 때 필요함
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy 연간관계의 주인이 아니다 (난 FK가 아니에요) DB에 칼럼을 만들지 마세요.
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
