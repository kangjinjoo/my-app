package com.cos.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// 원래는 @Repository에 적어야 스프링 Ioc에 bean으로 등록이 되는데!!
// JpaRepository를 extends하면 생략가능함
// JpaRepository안에 CRUD 함수를 들고 있음. 꺼내서 사용하면 됨

public interface BookRepository extends JpaRepository<Book, Long>  {

}
