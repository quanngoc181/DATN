package com.hust.datn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.entity.Product;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.specification.ProductSpecification;

@Controller
public class ProductManagementController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/admin/product-management")
	public String productManagement() {
		return "admin/product-management";
	}

	@GetMapping("/admin/product-management/datatable")
	@ResponseBody
	public DatatableDTO<Product> productDatatable(HttpServletRequest request) {
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		String value = request.getParameter("search[value]");
		String dir = request.getParameter("order[0][dir]");
		String order = request.getParameter("order[0][column]");
		String col = request.getParameter("columns[" + order + "][name]");

		Sort sort;
		if (dir.equalsIgnoreCase("asc"))
			sort = Sort.by(Sort.Direction.ASC, col);
		else
			sort = Sort.by(Sort.Direction.DESC, col);

		int countAll = (int) productRepository.count();
		int countFiltered = productRepository.count(ProductSpecification.containsTextInNameOrCode(value));
		List<Product> products = productRepository.findAll(ProductSpecification.containsTextInNameOrCode(value), PageRequest.of(start / length, length, sort));

		return new DatatableDTO<Product>(draw, countAll, countFiltered, products);
	}
}
