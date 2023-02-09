package com.jafa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String Home(Model model) {
		model.addAttribute("name","김민재");
		model.addAttribute("welcome","우리집");
		return "index";
	}
	
}
