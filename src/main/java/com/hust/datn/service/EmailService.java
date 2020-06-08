package com.hust.datn.service;

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

	public void sendResetPasswordEmail(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Q•Beverage - Reset password");
		message.setText("Dear Quan,\r\n đây là một bức thư test thôi nha !!!");
		emailSender.send(message);
	}
}
