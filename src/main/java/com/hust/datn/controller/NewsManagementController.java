package com.hust.datn.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.entity.News;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.NewsRepository;
import com.hust.datn.specification.NewsSpecification;

@Controller
public class NewsManagementController {
	@Autowired
	NewsRepository newsRepository;
	
	@GetMapping("/admin/news-management")
	public String newsManagement() {
		return "admin/news-management";
	}
	
	@GetMapping("/admin/news-management/datatable")
	@ResponseBody
	public DatatableDTO<News> newsDatatable(HttpServletRequest request) {
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

		int countAll = (int) newsRepository.count();
		int countFiltered = newsRepository.count(NewsSpecification.containsTextInTitleOrDescription(value));
		List<News> news = newsRepository.findAll(NewsSpecification.containsTextInTitleOrDescription(value),
				PageRequest.of(start / length, length, sort));

		return new DatatableDTO<News>(draw, countAll, countFiltered, news);
	}
	
	@GetMapping("/admin/news-management/add")
	@ResponseBody
	public ModelAndView addNews() {
		return new ModelAndView("partial/add-news");
	}
	
	@PostMapping("/admin/news-management/add")
	@ResponseBody
	public void addNews1(String title, String description, String content) {
		newsRepository.save(new News(null, title, description, content));
	}
	
	@PostMapping("/admin/news-management/delete")
	@ResponseBody
	public void deleteNews(String id) {
		newsRepository.deleteById(UUID.fromString(id));
	}
	
	@GetMapping("/admin/news-management/edit")
	@ResponseBody
	public ModelAndView editNews(String id) {
		News news = newsRepository.findById(UUID.fromString(id)).get();
		return new ModelAndView("partial/edit-news", "news", news);
	}
	
	@PostMapping("/admin/news-management/edit")
	@ResponseBody
	public void editNews1(String id, String title, String description, String content) throws InternalException {
		Optional<News> news = newsRepository.findById(UUID.fromString(id));
		if(!news.isPresent())
			throw new InternalException("Không tìm thấy tin tức");
		
		News n = news.get();
		n.setTitle(title);
		n.setDescription(description);
		n.setContent(content);
		
		newsRepository.save(n);
	}
}
