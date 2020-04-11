package com.hust.datn.controller;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.utilities.StringUtilities;

@Controller
public class UserController {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsManager userDetailsManager;

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	StringUtilities stringUtilities;

	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}

	@GetMapping("/user/login-success")
	public String loginSuccess(Authentication auth) {
		if (auth.getAuthorities().size() == 2)
			return "redirect:/admin";
		else
			return "redirect:/user";
	}

	@GetMapping("/user/change-password")
	public String changePassword() {
		return "user/change-password";
	}

	@PostMapping("/user/change-password")
	public String changePassword1(Authentication auth, String oldpass, String newpass, String confirmpass,
			RedirectAttributes ra) {
		String password = userDetailsManager.loadUserByUsername(auth.getName()).getPassword();

		if (newpass.equals(confirmpass)) {
			if (!oldpass.equals(newpass)) {
				if (passwordEncoder.matches(oldpass, password)) {
					userDetailsManager.changePassword(password, passwordEncoder.encode(newpass));
					ra.addFlashAttribute("message", "Đổi mật khẩu thành công");
				} else {
					ra.addFlashAttribute("message", "Sai mật khẩu");
				}
			} else {
				ra.addFlashAttribute("message", "Mật khẩu mới không được trùng mật khẩu cũ");
			}
		} else {
			ra.addFlashAttribute("message", "Mật khẩu nhập lại không khớp");
		}

		return "redirect:/user/change-password";
	}

	@GetMapping("/user/update-info")
	public String updateInfo(Authentication auth, Model model) {
		Account account = accountRepository.getByUsername(auth.getName());

		model.addAttribute("account", account);
		
		String encodedAvatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", encodedAvatar);
		
		List<ReceiveAddress> reList = account.getReceiveAddresses();
		Collections.reverse(reList);
		model.addAttribute("reList", reList);
		
		return "user/update-info";
	}

	@PostMapping("/user/update-info/basic")
	public String updateInfoBasic(Authentication auth, RegisterAccountCommand command, RedirectAttributes ra) {
		String username = auth.getName();

		Account account = accountRepository.getByUsername(username);
		account.setFirstName(command.firstName);
		account.setLastName(command.lastName);
		account.setBirthday(command.birthday);
		account.setGender(command.gender);

		accountRepository.save(account);

		ra.addFlashAttribute("basicmessage", "Cập nhật thông tin thành công");

		return "redirect:/user/update-info";
	}

	@PostMapping("/user/update-info/contact")
	public String updateInfoContact(Authentication auth, RegisterAccountCommand command, RedirectAttributes ra) {
		String username = auth.getName();

		Account account = accountRepository.getByUsername(username);
		account.setAddress(command.address);
		account.setPhone(command.phone);
		account.setEmail(command.email);

		accountRepository.save(account);

		ra.addFlashAttribute("contactmessage", "Cập nhật thông tin thành công");

		return "redirect:/user/update-info";
	}

	@PostMapping("/user/update-info/avatar")
	public String updateInfoAvatar(Authentication auth, @RequestParam("file") MultipartFile file, RedirectAttributes ra) {
		String extension = stringUtilities.getExtension(file.getOriginalFilename()).get();
		if(!extension.equals("jpg") && !extension.equals("png")) {
			ra.addFlashAttribute("avatarmessage", "Sai định dạng (jpg hoặc png)");
			return "redirect:/user/update-info";
		}
		
		try {
			byte[] bytes = file.getBytes();

			String username = auth.getName();

			Account account = accountRepository.getByUsername(username);
			account.setAvatar(bytes);

			accountRepository.save(account);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/update-info";
	}
}
