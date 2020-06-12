package com.hust.datn.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.News;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.NewsRepository;

@Controller
public class NewsController {
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/news")
	public String newsManagement(Model model, String id) {
		List<News> newsList = newsRepository.findAll();
		model.addAttribute("newsList", newsList);
		
		if(id == null) {
			if(newsList.size() > 0) {
				model.addAttribute("news", newsList.get(0));
				return "/news";
			} else {
				model.addAttribute("news", null);
				return "/news";
			}
		}
		
		Optional<News> news = newsRepository.findById(UUID.fromString(id));
		if(news.isPresent())
			model.addAttribute("news", news.get());
		else
			model.addAttribute("news", null);
		
		return "/news";
	}
	
	@GetMapping("/user/news")
	public String newsManagement1(Authentication auth, Model model, String id) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);
		String avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", avatar);
		
		List<News> newsList = newsRepository.findAll();
		model.addAttribute("newsList", newsList);
		
		if(id == null) {
			if(newsList.size() > 0) {
				model.addAttribute("news", newsList.get(0));
				return "user/news";
			} else {
				model.addAttribute("news", null);
				return "user/news";
			}
		}
		
		Optional<News> news = newsRepository.findById(UUID.fromString(id));
		if(news.isPresent())
			model.addAttribute("news", news.get());
		else
			model.addAttribute("news", null);
		
		return "user/news";
	}
}
