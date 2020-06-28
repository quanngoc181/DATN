package com.hust.datn.controller;

import java.security.cert.PKIXRevocationChecker.Option;
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

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.OptionItemDTO;
import com.hust.datn.entity.OptionItem;
import com.hust.datn.entity.ProductOption;
import com.hust.datn.enums.OptionType;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.ItemRepository;
import com.hust.datn.repository.OptionRepository;
import com.hust.datn.specification.ItemSpecification;

@Controller
public class OptionManagementController {
	@Autowired
	OptionRepository optionRepository;

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/admin/option-management")
	public String optionManagement() {
		return "admin/option-management";
	}

	@GetMapping("/admin/option-management/datatable")
	@ResponseBody
	public DatatableDTO<ProductOption> optionDatatable(HttpServletRequest request) {
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

		int countAll = (int) optionRepository.count();
		int countFiltered = optionRepository.countByNameContains(value);
		List<ProductOption> options = optionRepository.findByNameContains(value,
				PageRequest.of(start / length, length, sort));

		return new DatatableDTO<ProductOption>(draw, countAll, countFiltered, options);
	}

	@GetMapping("/admin/option-management/item-datatable")
	@ResponseBody
	public DatatableDTO<OptionItemDTO> itemDatatable(HttpServletRequest request) {
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

		int countAll = (int) itemRepository.count();
		int countFiltered = itemRepository.count(ItemSpecification.containsTextInNameOrCost(value));
		List<OptionItem> items = itemRepository.findAll(ItemSpecification.containsTextInNameOrCost(value),
				PageRequest.of(start / length, length, sort));

		List<OptionItemDTO> dtos = OptionItemDTO.fromItems(items);

		return new DatatableDTO<OptionItemDTO>(draw, countAll, countFiltered, dtos);
	}

	@PostMapping("/admin/option-management/add")
	@ResponseBody
	public void addOption(String name, int type) throws InternalException {
		if(name == null || name.trim().isEmpty())
			throw new InternalException("Tên tùy chọn không hợp lệ");
		
		optionRepository.save(new ProductOption(null, name, OptionType.values()[type]));
	}

	@PostMapping("/admin/option-management/edit")
	@ResponseBody
	public void editOption(String id, String name, int type) {
		Optional<ProductOption> optional = optionRepository.findById(UUID.fromString(id));
		if (optional.isPresent()) {
			ProductOption option = optional.get();
			option.setName(name);
			
			OptionType newType = OptionType.values()[type];
			if(option.getType().equals(OptionType.MULTIPLE) && newType.equals(OptionType.SINGLE)) {
				if(option.getItems().size() > 0) {
					for (OptionItem item : option.getItems()) {
						item.setDefault(false);
					}
					OptionItem first = (OptionItem) option.getItems().toArray()[0];
					first.setDefault(true);
				}
			}
			option.setType(newType);
			optionRepository.save(option);
		}
	}

	@PostMapping("/admin/option-management/delete")
	@ResponseBody
	public void deleteOption(String id) {
		UUID optionId = UUID.fromString(id);
		if(id != null)
			optionRepository.deleteById(optionId);
	}
	
	@PostMapping("/admin/option-management/add-item")
	@ResponseBody
	public void addItem(String optionId, String name, String cost, boolean isDefault) throws InternalException {
		if(name == null || name.trim().isEmpty())
			throw new InternalException("Tên lựa chọn không hợp lệ");
		
		int c;
		try {
			c = Integer.parseInt(cost);
			if(c < 0) throw new Exception();
		} catch (Exception e) {
			throw new InternalException("Giá lựa chọn không hợp lệ");
		}
		
		Optional<ProductOption> optional = optionRepository.findById(UUID.fromString(optionId));
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy tùy chọn");
		
		ProductOption option = optional.get();
		option.addItem(new OptionItem(null, name, c, isDefault));
		optionRepository.save(option);
	}
	
	@PostMapping("/admin/option-management/edit-item")
	@ResponseBody
	public void editItem(String id, String name, String cost, boolean isDefault) throws InternalException {
		if(name == null || name.trim().isEmpty())
			throw new InternalException("Tên lựa chọn không hợp lệ");
		
		int c;
		try {
			c = Integer.parseInt(cost);
			if(c < 0) throw new Exception();
		} catch (Exception e) {
			throw new InternalException("Giá lựa chọn không hợp lệ");
		}
		
		Optional<OptionItem> optional = itemRepository.findById(UUID.fromString(id));
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy lựa chọn");
		
		ProductOption option = optional.get().getOption();
		option.editItem(new OptionItem(UUID.fromString(id), name, c, isDefault));
		optionRepository.save(option);
	}
	
	@PostMapping("/admin/option-management/delete-item")
	@ResponseBody
	public void deleteItem(String id) throws InternalException {
		UUID itemId = UUID.fromString(id);
		
		Optional<OptionItem> optional = itemRepository.findById(itemId);
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy lựa chọn");
		
		ProductOption option = optional.get().getOption();
		
		option.deleteItem(itemId);

		optionRepository.save(option);
	}
}
