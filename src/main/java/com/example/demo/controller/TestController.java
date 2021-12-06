package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {
	
	@GetMapping
	public String testController() {
		return "Hello World";
	}
	
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! testGetMapping";
	}
	
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
		return "Hello Wordl! ID " + id;
	}
	
	@GetMapping("testRequestParam")
	public String testControllerWithRequestParam(@RequestParam(required = false) int id) {
		return "Hello World id "+id;
	}
	
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTo) {
		return "Hello World id "+testRequestBodyDTo.getId()+" Message "+testRequestBodyDTo.getMessage();
	}
	
	@GetMapping("testReponseBody")
	public ResponseDTO<String> testControllerReponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World Response DTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().error("error").data(list).build();
		return response;
	}
	
//	testReponseBody와 바디는 같지만 상태코드가 다름
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerREsponseEntity(){
		List<String> list = new ArrayList();
		list.add("Hello world reponse Entity 400");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	
	
}

