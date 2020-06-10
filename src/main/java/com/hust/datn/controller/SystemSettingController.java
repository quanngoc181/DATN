package com.hust.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemSettingController {
	@GetMapping("/admin/system-setting")
	public String index() {
		return "admin/system-setting";
	}
}
