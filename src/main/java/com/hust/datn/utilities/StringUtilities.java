package com.hust.datn.utilities;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class StringUtilities {
	public StringUtilities() {

	}

	public Optional<String> getExtension(String filename) {
		return Optional
				.ofNullable(filename.toLowerCase())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
