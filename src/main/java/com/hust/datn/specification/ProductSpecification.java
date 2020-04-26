package com.hust.datn.specification;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hust.datn.entity.Product;

public class ProductSpecification {
	public static Specification<Product> containsTextInAttributes(String text, List<String> attributes) {
		if (!text.contains("%")) {
			text = "%" + text + "%";
		}
		String finalText = text;
		return (root, query, builder) -> builder.or(root.getModel().getDeclaredSingularAttributes().stream()
				.filter(a -> attributes.contains(a.getName())).map(a -> builder.like(root.get(a.getName()), finalText))
				.toArray(javax.persistence.criteria.Predicate[]::new));
	}

	public static Specification<Product> containsTextInNameOrCode(String text) {
		return containsTextInAttributes(text, Arrays.asList("name", "productCode"));
	}
}
