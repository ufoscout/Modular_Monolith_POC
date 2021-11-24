package com.modular.build.web;

import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class ErrorHandler implements ErrorController {
 
    @RequestMapping("/error")
    public ModelAndView handleError() {
        ModelAndView model = new ModelAndView("forward:/index.html");
        model.setStatus(HttpStatus.OK);
        return model;
    }

}
