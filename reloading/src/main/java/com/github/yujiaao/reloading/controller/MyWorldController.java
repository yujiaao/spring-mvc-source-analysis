package com.github.yujiaao.reloading.controller;
import java.io.FileNotFoundException;

import javax.validation.Valid;

import com.github.yujiaao.reloading.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.yujiaao.reloading.validators.UserValidator;
@Controller
@RequestMapping("/myworld")
public class MyWorldController {


	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView user(){
		return new ModelAndView("user","user",new User());
	}

}