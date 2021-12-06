package com.example.demo;

import lombok.NonNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class DemoModel {
	
	@NonNull
	private String id;
}
