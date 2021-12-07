package com.example.demo.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;


@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	List<TodoEntity> findByUserId(String UserId);
	
	@Transactional
	void deleteByUserId(String userId);
	
//	@Query("SELECT * FROM Todo t where t.userId = ?1")
//	List<TodoEntity> findByUserId2(String UserId);
}
