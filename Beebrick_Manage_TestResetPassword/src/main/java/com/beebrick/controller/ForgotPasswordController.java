package com.beebrick.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.beebrick.entity.Employee;
import com.beebrick.service.EmployeeService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private EmployeeController empSevice;
	
	@Autowired
	private JavaMailSender mailSender;
	@GetMapping("/forgot_password")
	public String showForgotForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return "forgot_password";
	}
	
	@PostMapping("/forgot_password")
	public String forgotForm(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(45);
		
		try {
			empSevice.updateResetPasswordToken(token, email);
			//Gena
			String resetPasswordLink= Utility.getSiteURL(request)+ "/reset_password?token=" + token;
			System.out.println(resetPasswordLink);
			//sendEmail
			sendEmail(email, resetPasswordLink );
			model.addAttribute("message","Chúng tôi đã gửi đường dẫn để bạn có thể cập nhật mật khẩu. vui lòng kiểm tra");
		} catch (EmployeeNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Lỗi khi đang thực hiện gửi e-mail");
			
		} 
		model.addAttribute("pageTitle", "Forgot Password");
		return "forgot_password";
	}

	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contacsuportbeebrick@gmail.com","Beebrick support");
		helper.setTo(email);
		
		String subject = "Đây là đường dẫn để bạn cập nhật lại mật khẩu";
		
		String content = "<p>Xin chào, </p>"
				+ "<p>Bạn có yêu cầu để cập nhật lại mật khẩu</p>"
				+"<p>Nhấn vào đường dẫn để thay đổi mật khẩu</p>"
				+"<p><b><a href=\""+resetPasswordLink +"\">Thay đổi mật khẩu</a></b></p>"
				+"<p>Bỏ qua e-mail này nếu bạn đã nhớ mật khẩu, hoặc bạn không có yêu cầu này</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		
		mailSender.send(message);
	}
	
	@GetMapping("/reset_password")
	public String showReset(@Param(value = "token") String token, Model model) {
		Employee emp = empSevice.get(token);
		if(emp == null) {
			model.addAttribute("title", "Cập nhật mật khẩu");
			model.addAttribute("message", "Lỗi token");
			return "message";
		}
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Mật khẩu của bạn");
		return "forgot_password_step2";
	}
	
	@PostMapping("/reset_password")
	public String ResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		Employee emp = empSevice.get(token);
		
		if(emp == null) {
			model.addAttribute("title", "Cập nhật mật khẩu");
			model.addAttribute("message", "Lỗi token");
			
		} else {
			empSevice.updatePassword(emp, password);
			model.addAttribute("message", "Cập nhật mật khẩu mới thành công");
			
		}
		return "message";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
