package com.beebrick.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String login(@RequestParam(required = false) String message, final Model model, HttpSession session) {

		if (message != null && !message.isEmpty()) {
			if (message.equals("timeout")) {
				model.addAttribute("message", "Time out");
			}
			if (message.equals("max_session")) {
				model.addAttribute("message", "This accout has been login from another device!");
			}
			if (message.equals("logout")) {
				model.addAttribute("message", "Logout!");
			}
			if (message.equals("error")) {
				model.addAttribute("message", "Login Failed!");
			}
		}
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String submit(String username, String fullname, HttpSession session) {
		session.setAttribute("username", username);
		session.setAttribute("fullname", fullname);
		return "login_info";
	}
	@RequestMapping(value = "admin/index")
	public String showMain() {
		return "admin/index";
	}
}
