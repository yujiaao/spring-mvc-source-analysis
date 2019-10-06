package com.github.yujiaao.springformtags;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/reg")
@ControllerAdvice(basePackageClasses = {RegistrationFormController.class})
public class RegistrationFormController{



    @PostMapping("/index")
    protected ModelAndView onSubmit(Registration reg)  {

        String uname = reg.getUsername();
        String fname = reg.getFname();

        String gender = reg.getGender();
        String country = reg.getCountry();
        String cb = reg.getCb();
        String addr = reg.getAddr();

        ModelAndView mv = new ModelAndView("success");

        mv.addObject("uname", uname);
        mv.addObject("fname", fname);
        mv.addObject("gender", gender);
        mv.addObject("country", country);
        mv.addObject("cb", cb);
        mv.addObject("addr", addr);

        return mv;
    }



    @GetMapping("/index")
    public ModelAndView index(Registration reg){
        return new ModelAndView("index","registration", reg);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView myError(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("error");
        return mav;
    }


}
