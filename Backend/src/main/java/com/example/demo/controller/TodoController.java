package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;

	//testTodo 메서드
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<String>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
//	생성
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){
		try {
//			(1). TodoEntity 로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
//			(2). id를 null로 초기화
			entity.setId(null);
			
//			(3). 임시 사용자 아이디 지정, 4장에서 제대로 다룸
			entity.setUserId(userId);
			
//			(4). 서비스를 이용해 Todo 엔티티 생성
			List<TodoEntity> entities = service.create(entity);
			
//			(5). 자바 스트림 이용해 리턴된 엔티티 리스트 TodoDTO 리스토 변경
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
//			(6). 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
//			(7). ResponseDTO 리턴
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
//			(8). 예외가 있는 경우 dto 대신 error 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.ok().body(response);
		}
	}
	
	
//	검색
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){

//		(1). 서비스의 retrieve 메서드 이용해 Todo 리스트 가져옴
		List<TodoEntity> entities = service.retrieve(userId);


//		(2). 자바 스트림 (엔터티 리스트 -> TodoDTO 리스트)
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		

//		(3). TodoDTO 리스트를 이용해 ResponseDTO 초기화 
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
//	업데이트
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){

//		(1). dto를 entity로 변환
		TodoEntity entity = TodoDTO.toEntity(dto);
		
//		(2). id 초기화
		entity.setUserId(userId);
		
//		(3). 서비스를 이용해 entity 업데이트
		List<TodoEntity> entities = service.update(entity);
		
//		(4). 자바 스트림 엔티티 리스트 -> TodoDTO 리스트
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
//		(5). 변환된 TodoDTO 리스트 이용해 ResponseDTO 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

//		(6). ResponseEntity 리턴
		return ResponseEntity.ok().body(response);
	}
	
	
//	삭제
	@DeleteMapping
	public ResponseEntity<?> deleteDTO(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){
		try {
//			(1). TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);

//			(2). 사용자 아이디 설정
			entity.setUserId(userId);
			
//			(3). 구현한 서비스로 엔티티 삭제
			List<TodoEntity> entities = service.delete(entity);
			
//			(4). stream 이용해 Entitiy 리스트 -> TodoDTO 리스트
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
//			(5). RepsonseDTO 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
//			(6). ResponseDTO 리턴
			return ResponseEntity.ok().body(response);
		}catch (Exception e){
//			(7). 오류가 있는 경우 에러 메시지 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.ok().body(response);
		}
	}
	
	
//	내 마음대로 구현
	@PutMapping("/mytest")
	public ResponseEntity<?> todoCreate(@RequestBody TodoEntity todoEntity ){
		service.todoCreate(todoEntity);
		
		return ResponseEntity.ok().body("todo Create");
	}
	
	@GetMapping("/mytest")
	public ResponseEntity<?> todoRetrieve(@RequestParam String userId){
		List<TodoEntity> todoRetrieve = service.todoRetrieve(userId);
		
		return ResponseEntity.ok().body(todoRetrieve);
	}
	
	@PostMapping("/mytest")
	public ResponseEntity<?> todoUpdate(@RequestParam String id, @RequestParam String title){
		TodoEntity entity = service.todoUpdate(id, title);
		return ResponseEntity.ok().body(entity);
	}
	
	@DeleteMapping("/mytest")
	public ResponseEntity<?> todoDelete(@RequestParam String userId){
		service.todoDelete(userId);
		return ResponseEntity.ok().body("todo Delete");
	}
	
	
	
}
