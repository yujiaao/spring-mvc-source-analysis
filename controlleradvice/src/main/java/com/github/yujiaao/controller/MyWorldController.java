package com.github.yujiaao.controller;
import java.io.FileNotFoundException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.github.yujiaao.User;
import com.github.yujiaao.validators.UserValidator;
@Controller
@RequestMapping("/")
public class MyWorldController {
	@Autowired
	private UserValidator userValidator;

	@GetMapping({"signup",""})
	public ModelAndView signup(){
		System.out.println("MyWorldController::signup");
		return new ModelAndView("user","user",new User());
	}

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@PostMapping(value="save")
	public String save(@ModelAttribute("user") @Valid User user,BindingResult result, ModelMap model)
			                                                throws FileNotFoundException {

		System.out.println("MyWorldController::save");

	    if(result.hasErrors()) {
	    	return "user";
	    }

	    //输入用户名为: exception, 检查ControllerAdvice是否能拦截到异常
	    if(user.getName().equals("exception")) {
	        throw new FileNotFoundException("Error found.");	
	    }
	    System.out.println("Name:"+ user.getName());
	    System.out.println("Date of Birth:"+ user.getDob());
		return "success";
	}

	@GetMapping("null")
	public String throwNull(){
		User u=null;
		u.getDob();
		return "success";
	}



}