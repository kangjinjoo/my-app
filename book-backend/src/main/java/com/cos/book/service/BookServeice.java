package com.cos.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor // final이 있는 애들의 cunstructor를 만들어줌, 자동 di가 됨
@Service // 기능을 정의할 수 있고, 트랙잭션을 관리할 수 있음.
public class BookServeice {
	private final BookRepository bookRepository;
	
	
	@Transactional  // 서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다
	public Book 저장하기(Book book) {
		return bookRepository.save(book);
	}
	
	// 람다식으로 하면 타입 몰라도 됨 
	@Transactional(readOnly=true)  // JPA 변경감지라는 내부기능활성화가 사라져서 쓸데없는 연산이 줄어들고, 
								   //update시의 정합성을 유지해줌
								   // insert의 유령데이터현생(팬텀현상)못막음.
								   // select 할때마다 걸어줘~
	public Book 한건가져오기(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!"));
	}
	
	@Transactional(readOnly=true)
	public List<Book> 모두가져오기(){
		return bookRepository.findAll();
	}
	
	@Transactional
	public Book 수정하기(Long id,Book book) {
		// 더티체킹 update치기
		Book bookEntity=bookRepository.findById(id)  // DB에 있는 실제값을 들고와서 영속화 시킴
				// 영속화가 되었다는건 스프링 내부 메모리 공간에 쟤를 딱 들고 있음
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!")); // 영속화(book오브젝트)->영속성 컨텍스트 보관
		bookEntity.setTitle(book.getTitle()); // 그 데이터를 변경시킴
		bookEntity.setAuthor(book.getAuthor());
		return bookEntity;
	}  // 함수 종료 => 트랜잭션 종료 => 영속화 되어있는 데이터를 db로 갱신(flush) => 이때 commit! 이걸 더티체킹이라고함
	
	public String 삭제하기(Long id) {
		bookRepository.deleteById(id);// 오류가 터지면 익셉션을 타니까 신경쓰지 말고
		return "ok";  // ok라는건 정상적으로 되었다.
	}
	
}
