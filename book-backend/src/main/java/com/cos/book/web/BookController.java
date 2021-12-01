package com.cos.book.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.book.domain.Book;
import com.cos.book.service.BookServeice;

import lombok.RequiredArgsConstructor;

// final을 걸면 @RequiredArgsConstructor를 통해서 생성자가 자동으로 만들어지고
// 스프링 ioc컨테이너에 메모리가 올라감. 스프링은 생성자를 통해서  ioc컨테이너에 올려주니까ㅣ
@RequiredArgsConstructor
@RestController
public class BookController {
	
	private final BookServeice bookServeice;
	
	@PostMapping("/book")
	// 타입이 <?>면 알아서 데이터를 보고 그타입으로 리턴된다.
	public ResponseEntity<?>save(@RequestBody Book book){  // @RequestBody 걸면 json으로 받음
		
return new ResponseEntity<>(bookServeice.저장하기(book),HttpStatus.CREATED);
	// 업그레이드 된 버전부터는 ResponseEntity<> 괄호에 타입을 꼭 정의해주지않아도 됨.
	}
	
	
	//ResponseEntity = http HttpStatus 코드를 같이 보낼 수 있음
	@GetMapping("/book")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(bookServeice.모두가져오기(),HttpStatus.OK);  // 200을 응답
	}

	@CrossOrigin  // 외부에서 들어오는 자바요청을 허용해줌
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findAll(@PathVariable Long id){
		return new ResponseEntity<>(bookServeice.한건가져오기(id),HttpStatus.OK);  // 200을 응답
	}
	
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book ){ // 업데이트 칠때는 데이터를 두건받아야함, 한건 안됨.
		return new ResponseEntity<>(bookServeice.수정하기(id,book),HttpStatus.OK);  // 200을 응답
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deletById(@PathVariable Long id){ 
		return new ResponseEntity<>(bookServeice.삭제하기(id),HttpStatus.OK); 
	}

}
