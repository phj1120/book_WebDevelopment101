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

	//testTodo �޼���
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<String>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
//	����
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){
		try {
//			(1). TodoEntity �� ��ȯ
			TodoEntity entity = TodoDTO.toEntity(dto);
			
//			(2). id�� null�� �ʱ�ȭ
			entity.setId(null);
			
//			(3). �ӽ� ����� ���̵� ����, 4�忡�� ����� �ٷ�
			entity.setUserId(userId);
			
//			(4). ���񽺸� �̿��� Todo ��ƼƼ ����
			List<TodoEntity> entities = service.create(entity);
			
//			(5). �ڹ� ��Ʈ�� �̿��� ���ϵ� ��ƼƼ ����Ʈ TodoDTO ������ ����
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
//			(6). ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO �ʱ�ȭ
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
//			(7). ResponseDTO ����
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
//			(8). ���ܰ� �ִ� ��� dto ��� error �޽����� �־� ����
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.ok().body(response);
		}
	}
	
	
//	�˻�
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){

//		(1). ������ retrieve �޼��� �̿��� Todo ����Ʈ ������
		List<TodoEntity> entities = service.retrieve(userId);


//		(2). �ڹ� ��Ʈ�� (����Ƽ ����Ʈ -> TodoDTO ����Ʈ)
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		

//		(3). TodoDTO ����Ʈ�� �̿��� ResponseDTO �ʱ�ȭ 
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
//	������Ʈ
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){

//		(1). dto�� entity�� ��ȯ
		TodoEntity entity = TodoDTO.toEntity(dto);
		
//		(2). id �ʱ�ȭ
		entity.setUserId(userId);
		
//		(3). ���񽺸� �̿��� entity ������Ʈ
		List<TodoEntity> entities = service.update(entity);
		
//		(4). �ڹ� ��Ʈ�� ��ƼƼ ����Ʈ -> TodoDTO ����Ʈ
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
//		(5). ��ȯ�� TodoDTO ����Ʈ �̿��� ResponseDTO �ʱ�ȭ
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

//		(6). ResponseEntity ����
		return ResponseEntity.ok().body(response);
	}
	
	
//	����
	@DeleteMapping
	public ResponseEntity<?> deleteDTO(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId){
		try {
//			(1). TodoEntity�� ��ȯ
			TodoEntity entity = TodoDTO.toEntity(dto);

//			(2). ����� ���̵� ����
			entity.setUserId(userId);
			
//			(3). ������ ���񽺷� ��ƼƼ ����
			List<TodoEntity> entities = service.delete(entity);
			
//			(4). stream �̿��� Entitiy ����Ʈ -> TodoDTO ����Ʈ
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
//			(5). RepsonseDTO �ʱ�ȭ
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
//			(6). ResponseDTO ����
			return ResponseEntity.ok().body(response);
		}catch (Exception e){
//			(7). ������ �ִ� ��� ���� �޽��� �־� ����
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.ok().body(response);
		}
	}
	
	
//	�� ������� ����
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
