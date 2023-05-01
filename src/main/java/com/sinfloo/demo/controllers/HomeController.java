package com.sinfloo.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping({"/index", "/", "/home"})
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            // Usuario autenticado, hacer algo aquí
            return "admin/index";
        } else {
        	return "redirect:/login/login";
        }
        
    }
    
    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @GetMapping("/forbidden")
    public String forbidden(){
        return "forbidden";
    }

}
