package com.hust.datn.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	public JavaMailSender emailSender;

	public EmailService() {
	}

	public void sendResetPasswordEmail(String to, UUID token, String username) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Q•Beverage - Lấy lại mật khẩu");
		message.setText("Chào quý khách,\r\nChúng tôi nhận được yêu cầy lấy lại mật khẩu từ tài khoản " + username +
				". Vui lòng truy cập đường link bên dưới để thực hiện lấy lại mật khẩu:\r\nhttps://localhost/reset-password?rpt=" + token +
				"\r\nLưu ý: link trên chỉ có tác dụng trong vòng 1h");
		emailSender.send(message);
	}
}
