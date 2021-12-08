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

//	생성
	public List<TodoEntity> create(final TodoEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity id : {} is saved", entity.getUserId());
		
		return repository.findByUserId(entity.getUserId());
	}


//	검색
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
//	수정 람다식
	public List<TodoEntity> updateHigh(final TodoEntity entity){

//		(1). 저장할 엔티티가 유효한지 확인
		validate(entity);
		
//		(2). 넘겨받은 엔티티의 id를 이용해 TodoEntitiy 가져옴
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
//		(3). 반환된 TodoEntitiy 가 존재하면 새 entitiy 값으로 덮어 씌움
		original.ifPresent(todo->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
//		(4). DB에 새 값 저장
			repository.save(todo);
		});
		
//		(5). 사용자의 모든 Todo 리스트 반환
		return retrieve(entity.getUserId());
	}
	
//	수정
	public List<TodoEntity> update(final TodoEntity entity){

//		(1). 저장할 엔티티가 유효한지 확인
		validate(entity);
		
//		(2). 넘겨받은 엔티티의 id를 이용해 TodoEntitiy 가져옴
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
//		(3). 반환된 TodoEntitiy 가 존재하면 새 entitiy 값으로 덮어 씌움
		if(original.isPresent()) {
			final TodoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
//		(4). DB에 새 값 저장
			repository.save(todo);
		}
		
//		(5). 사용자의 모든 Todo 리스트 반환
		return retrieve(entity.getUserId());
	}
	
//	삭제
	public List<TodoEntity> delete(final TodoEntity entity){
//		(1). 검증
		validate(entity);
		
		try {
//			(2). 엔티티 삭제
			repository.delete(entity);
		}catch(Exception e) {
//			(3). 오류 발생시 id와 exception 로깅
			log.error("error deleting entity ", entity.getId(), e);
//			(4). 컨트롤러에게 exception 리턴
			throw new RuntimeException("error deleting entity "+entity.getId());
		}
//		(5). 새 todo 리스트 반환
		return retrieve(entity.getUserId());
	}

	
//	검증
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
