package com.cos.book.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data  // getter setter 만들고
@Entity  // 서버 실행시에 Object Relation Mapping이 된다.(ORM)즉, 서버실행시에 테이블치 H2에 생성됨
public class Book { 
	@Id  // PK를 해당 변수로 하겠다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 해당데이터베이스 번호증가 전략을 따라가겠다.
	private Long id;
	
    private String title;
    private String author;
    
	
}
