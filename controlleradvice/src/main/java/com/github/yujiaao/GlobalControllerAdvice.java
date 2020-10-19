package com.github.yujiaao;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.yujiaao.controller.MyWorldController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 用basePackages 不利用重构
//@ControllerAdvice(basePackages = {"com.github.yujiaao.controller"})
@ControllerAdvice(basePackageClasses = {MyWorldController.class})
public class GlobalControllerAdvice {
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        System.out.println("GlobalControllerAdvice::dataBinding");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute
    public void globalAttributes(Model model) {
        System.out.println("GlobalControllerAdvice::globalAttributes");
        model.addAttribute("msg", "Welcome to My World!");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public String myError(Exception exception, HttpServletRequest request) {
        request.setAttribute("exception", exception);

        request.setAttribute("javax.servlet.error.status_code", 500);

        return "forward:error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String myRuntimeError(Exception exception, HttpServletRequest request) {
        request.setAttribute("exception", exception);

        exception.printStackTrace();

        request.setAttribute("javax.servlet.error.status_code", 500);

        return "forward:error";
    }

}
