package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.cos.book.service.BookServeice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

// 단위 테스트 (컨트롤 관련된 로직만 테스트함)
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // ioc 환경이 bean 등록됨.
	private BookServeice bookServeice;
	
	// BBDmokito 패턴 
	@Test
	public void save_테스트() throws Exception {
		// given (테스트를 하기 위한 준비)\
		Book book = new Book(null,"스프링 따라하기","코스");
		String content = new ObjectMapper().writeValueAsString(book);
		// 미리 행동 지정하기
		when(bookServeice.저장하기(book)).thenReturn(new Book(1L,"스프링 따라하기","코스"));

		// when (실제로 테스트 실행)
		ResultActions resultAction =  mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증) 내가 테스트 하고 싶은거 다 넣으면 됨
		resultAction
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.title").value("스프링 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_테스트() throws Exception{
		// given 미리 결과를 만들어놓기
		List<Book> books = new ArrayList<Book>();
		books.add(new Book(1L,"스프링부트 따라하기","코스"));
		books.add(new Book(2L,"리엑트 따라하기","코스"));
		when(bookServeice.모두가져오기()).thenReturn(books);
		
		//when
		ResultActions resultAction = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(2)))
		.andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
		
		
	}
	
	@Test
	public void findById_테스트() throws Exception{
	  //given
	  Long id = 1L;
	  // 내가 기대하는 결과
	  when(bookServeice.한건가져오기(id)).thenReturn(new Book(1L,"자바 공부하기","살"));
	  
	  //when
	  ResultActions resultAction = mockMvc.perform(get("/book/{id}",id)
			  .accept(MediaType.APPLICATION_JSON_UTF8));
	  
	  //then
	  resultAction
	  .andExpect(status().isOk())
	  .andExpect(jsonPath("$.title").value("자바 공부하기"))
	  .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void update_테스트() throws Exception{
	  //given
	  Long id = 1L;
		Book book = new Book(null,"C++따라하기","코스");
		String content = new ObjectMapper().writeValueAsString(book);
		// 미리 행동 지정하기
		when(bookServeice.수정하기(id,book)).thenReturn(new Book(1L,"C++따라하기","코스"));
	  
	  //when
		ResultActions resultAction =  mockMvc.perform(put("/book/{id}",id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

	  
	  //then
	  resultAction
	  .andExpect(status().isOk())
	  .andExpect(jsonPath("$.title").value("C++따라하기"))
	  .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void delet_테스트() throws Exception{
	  //given
	  Long id = 1L;
		// 미리 행동 지정하기
		when(bookServeice.삭제하기(id)).thenReturn("ok");
	  
	  //when
		ResultActions resultAction =  mockMvc.perform(delete("/book/{id}",id)
				.accept(MediaType.TEXT_PLAIN));  // 텍스트만 반환받으면 되니까, 컨트롤러에 삭제하기는 ok를 반환

	  
	  //then
	  resultAction
	  .andExpect(status().isOk())  // 상태코가 맞는지
	  .andDo(MockMvcResultHandlers.print());
	  
	  // 문자를 응답하면 이렇게 해야함 (걍 외워)
	  MvcResult requesResult=resultAction.andReturn();
	  String result=requesResult.getResponse().getContentAsString();
	  
	  assertEquals("ok", result);  //ok가 맞는지
		
	}
	
	
}
