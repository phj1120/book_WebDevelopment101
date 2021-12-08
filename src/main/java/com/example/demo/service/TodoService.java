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
//		����
		TodoEntity entity = TodoEntity.builder().title("first todo item").build();
//		����
		repository.save(entity);
//		�˻�
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}

//	����
	public List<TodoEntity> create(final TodoEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity id : {} is saved", entity.getUserId());
		
		return repository.findByUserId(entity.getUserId());
	}


//	�˻�
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
//	���� ���ٽ�
	public List<TodoEntity> updateHigh(final TodoEntity entity){

//		(1). ������ ��ƼƼ�� ��ȿ���� Ȯ��
		validate(entity);
		
//		(2). �Ѱܹ��� ��ƼƼ�� id�� �̿��� TodoEntitiy ������
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
//		(3). ��ȯ�� TodoEntitiy �� �����ϸ� �� entitiy ������ ���� ����
		original.ifPresent(todo->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
//		(4). DB�� �� �� ����
			repository.save(todo);
		});
		
//		(5). ������� ��� Todo ����Ʈ ��ȯ
		return retrieve(entity.getUserId());
	}
	
//	����
	public List<TodoEntity> update(final TodoEntity entity){

//		(1). ������ ��ƼƼ�� ��ȿ���� Ȯ��
		validate(entity);
		
//		(2). �Ѱܹ��� ��ƼƼ�� id�� �̿��� TodoEntitiy ������
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
//		(3). ��ȯ�� TodoEntitiy �� �����ϸ� �� entitiy ������ ���� ����
		if(original.isPresent()) {
			final TodoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
//		(4). DB�� �� �� ����
			repository.save(todo);
		}
		
//		(5). ������� ��� Todo ����Ʈ ��ȯ
		return retrieve(entity.getUserId());
	}
	
//	����
	public List<TodoEntity> delete(final TodoEntity entity){
//		(1). ����
		validate(entity);
		
		try {
//			(2). ��ƼƼ ����
			repository.delete(entity);
		}catch(Exception e) {
//			(3). ���� �߻��� id�� exception �α�
			log.error("error deleting entity ", entity.getId(), e);
//			(4). ��Ʈ�ѷ����� exception ����
			throw new RuntimeException("error deleting entity "+entity.getId());
		}
//		(5). �� todo ����Ʈ ��ȯ
		return retrieve(entity.getUserId());
	}

	
//	����
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
	
	
	
	
//	�̸� �ǽ� �غ� ��
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
