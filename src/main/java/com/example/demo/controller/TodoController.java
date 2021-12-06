package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDTO;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	//testTodo ¸Þ¼­µå
	@GetMapping
	public ResponseEntity<TodoDTO> testTodo(){
		
		TodoDTO todoDTO = TodoDTO.builder().id("hj").title("student").build();
		
		return ResponseEntity.ok().body(todoDTO);
		
	}
}
