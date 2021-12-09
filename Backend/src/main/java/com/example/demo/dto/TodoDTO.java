package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
//	TodoDTO를 받아 TodoEntity로 반환하기 위해 생성
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
	
}

//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class ResponseDTO {
//	private String error;
////	private List<T> data;
//}
