package com.evaluation.erpnext_spring.controller;

import com.evaluation.erpnext_spring.dto.LoginRequestDTO;
import com.evaluation.erpnext_spring.dto.LoginResponseDTO;
import com.evaluation.erpnext_spring.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginRequest", new LoginRequestDTO());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginRequestDTO loginRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        LoginResponseDTO response = authService.login(loginRequest);
        
        if (response.isSuccess()) {
            session.setAttribute("sid", response.getSessionId());
            session.setAttribute("fullName", response.getFullName());
            modelAndView.setViewName("redirect:/dashboard");
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("error", response.getMessage());
            modelAndView.addObject("loginRequest", loginRequest);
        }
        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboard(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String sid = (String) session.getAttribute("sid");
        String fullName = (String) session.getAttribute("fullName");
        
        if (sid == null || sid.isEmpty()) {
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("dashboard");
            modelAndView.addObject("fullName", fullName);
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}