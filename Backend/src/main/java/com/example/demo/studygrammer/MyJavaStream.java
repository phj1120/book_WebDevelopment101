package com.example.demo.studygrammer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

public class MyJavaStream {

	public static void main(String[] args) {
		TodoEntity entity1 = new TodoEntity().builder().id("1234").title("first").build();
		TodoEntity entity2 = new TodoEntity().builder().id("5678").title("second").build();
		
		List<TodoEntity> entities = new ArrayList<TodoEntity>();
		entities.add(entity1);
		entities.add(entity2);
		System.out.println("entities");
		System.out.println(entities);
		System.out.println();
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		System.out.println("dto");
		System.out.println(dtos);
		System.out.println();

		// ºÐÇØ
		Stream<TodoEntity> stream = entities.stream();
		System.out.println("Stream");
		System.out.println(stream);
		System.out.println();
		
		Stream<TodoDTO> map = stream.map(TodoDTO::new);
		System.out.println("map");
		System.out.println(map.toString());
		System.out.println();
		
		List<TodoDTO> collect = map.collect(Collectors.toList());
		System.out.println("collect");
		System.out.println(collect);
		System.out.println();
	}
}
