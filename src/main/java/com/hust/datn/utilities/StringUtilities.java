package com.hust.datn.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class StringUtilities {
	public StringUtilities() {
		super();
	}

	public Optional<String> getExtension(String filename) {
		return Optional
				.ofNullable(filename.toLowerCase())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	
	public List<UUID> uuidsFromString(String str) {
		List<UUID> uuids = new ArrayList<>();
		if(str == null || str.isEmpty())
			return uuids;
		
		String[] ids = str.split(";");
		for (String id : ids) {
			uuids.add(UUID.fromString(id));
		}
		
		return uuids;
	}
}
