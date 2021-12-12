package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {

	private static final String SECRET_KEY = "4c6ee481-2026-46c1-81bd-37656838eb8c";
	
//	JWT ���̺귯���� �̿��� JWT ��ū ����
	public String create(UserEntity userEntity) {
		Date expiryDate = Date.from(Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		return Jwts.builder()
//				����� �� ���� �� �����ϱ� ���� ��ũ�� Ű
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)

//				���̷ε忡 �� ����
				.setSubject(userEntity.getId())
				.setIssuer("demo app")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				
				.compact();
	}
	
//	��ū ���ڵ� �� �Ľ��� ��ū�� ���� ���� Ȯ�� ��
//	�츮�� ���ϴ� subject (����ھ��̵�)�� ������
	public String validateAndGetUserId(String token) {
//		parseClaimsJws �޼��尡 Base64�� ���ڵ� �� �Ľ�
		Claims claims = Jwts.parser()
//		����� ���̷ε带 setSigningKey�� �Ѿ�� ��ũ�� Ű�� ������
		.setSigningKey(SECRET_KEY)
//		token�� ����� ���� 
//		�������� �ʾҴٸ� ���̷ε�(Claims) ���� 
//		������� ���� ����
		.parseClaimsJws(token)
//		userId �� �ʿ��ϹǷ� getBody
		.getBody();

		return claims.getSubject();
	}
}
