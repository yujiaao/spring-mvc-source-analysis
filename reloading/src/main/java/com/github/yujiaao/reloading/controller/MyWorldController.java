package com.github.yujiaao.reloading.controller;

import com.github.yujiaao.reloading.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/myworld")
public class MyWorldController {


	@GetMapping(value="/")
	public ModelAndView user(){
		return new ModelAndView("user","user",new User());
	}

}