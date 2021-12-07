package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {

	@Autowired
	private TodoRepository repository;
	
	public String testService() {
//		생성
		TodoEntity entity = TodoEntity.builder().title("first todo item").build();
//		저장
		repository.save(entity);
//		검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}

	public List<TodoEntity> create(final TodoEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity id : {} is saved", entity.getUserId());
		
		return repository.findByUserId(entity.getUserId());
	}

	private void validate(final TodoEntity entity) {
		if(entity==null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
	
		if(entity.getUserId()==null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}
	
	
	
	
	
//	미리 실습 해본 것
	public void todoCreate(TodoEntity todoEntity) {
		repository.save(todoEntity);
	}
	
	public List<TodoEntity> todoRetrieve(String userId) {
		List<TodoEntity> entity = repository.findByUserId(userId);

		return entity;
	}

	public TodoEntity todoUpdate(String id, String title) {
		TodoEntity entity = repository.findById(id).get();
		entity.setTitle(title);
		repository.save(entity);
		
		return entity;
	}
	
	public void todoDelete(String userId) {
		repository.deleteByUserId(userId);
	}
	
}
