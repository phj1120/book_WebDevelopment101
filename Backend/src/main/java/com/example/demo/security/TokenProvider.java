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
	
//	JWT 라이브러리를 이용해 JWT 토큰 생성
	public String create(UserEntity userEntity) {
		Date expiryDate = Date.from(Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		return Jwts.builder()
//				헤더에 들어갈 내용 및 서명하기 위한 시크릿 키
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)

//				페이로드에 들어갈 내용
				.setSubject(userEntity.getId())
				.setIssuer("demo app")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				
				.compact();
	}
	
//	토큰 디코딩 및 파싱해 토큰의 위조 여부 확인 후
//	우리가 원하느 subject (사용자아이디)를 리턴함
	public String validateAndGetUserId(String token) {
//		parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
		Claims claims = Jwts.parser()
//		헤더와 페이로드를 setSigningKey로 넘어온 시크릿 키로 서명한
		.setSigningKey(SECRET_KEY)
//		token의 서명과 비교해 
//		위조되지 않았다면 페이로드(Claims) 리턴 
//		위조라면 예외 날림
		.parseClaimsJws(token)
//		userId 가 필요하므로 getBody
		.getBody();

		return claims.getSubject();
	}
}
