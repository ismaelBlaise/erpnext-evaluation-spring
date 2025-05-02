package com.evaluation.erpnext_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    @GetMapping
    public ModelAndView paymentForm(@RequestParam String facture){
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","invoices/payment");
        return modelAndView;
    }
}
