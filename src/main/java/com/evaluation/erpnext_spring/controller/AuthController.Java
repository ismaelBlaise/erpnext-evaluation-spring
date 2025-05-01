package com.evaluation.erpnext_spring.controller;



import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest, Model model, HttpSession session) {
        LoginResponseDTO response = authService.login(loginRequest);
        
        if (response.isSuccess()) {
            
            session.setAttribute("sid", response.getSessionId());
            session.setAttribute("fullName", response.getFullName());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", response.getMessage());
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        String sid = (String) session.getAttribute("sid");
        String fullName = (String) session.getAttribute("fullName");
        
        if (sid == null || sid.isEmpty()) {
            return "redirect:/login";
        }
        
        model.addAttribute("fullName", fullName);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}