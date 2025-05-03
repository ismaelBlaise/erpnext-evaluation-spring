package com.evaluation.erpnext_spring.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evaluation.erpnext_spring.dto.invoices.PurchaseInvoice;
import com.evaluation.erpnext_spring.dto.payments.PaymentDTO;
import com.evaluation.erpnext_spring.service.PaymentService;
import com.evaluation.erpnext_spring.service.PurchaseInvoiceService;

import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/payments")
public class PaymentController {

    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private PurchaseInvoiceService purchaseInvoiceService;

    @GetMapping
    public ModelAndView paymentForm(@RequestParam String facture, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("template");
        
        try {
            PurchaseInvoice invoice = purchaseInvoiceService.getPurchaseInvoiceByName(session, facture);
            
            
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setInvoiceName(invoice.getName());
            paymentDTO.setCompany(invoice.getCompany());
            paymentDTO.setPostingDate(invoice.getPostingDate());
            paymentDTO.setPaidAmount(invoice.getOutstandingAmount());
            paymentDTO.setAllocatedAmount(invoice.getOutstandingAmount());
            paymentDTO.setParty(invoice.getSupplier());  
            
            modelAndView.addObject("page", "invoices/payment");
            modelAndView.addObject("invoice", invoice);
            modelAndView.addObject("paymentDTO", paymentDTO); 
            
        } catch (Exception e) {
            modelAndView.addObject("error", "Erreur lors de la récupération de la facture: " + e.getMessage());
            modelAndView.addObject("page","error");
        }
        
        return modelAndView;
    }



    @PostMapping("/process")
    public String processPayment(
            @ModelAttribute PaymentDTO paymentDTO,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        try {
             
            if (paymentDTO.getPaidAmount() == null || paymentDTO.getPaidAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant payé doit être supérieur à zéro");
            }
            paymentDTO.setReceivedAmount(paymentDTO.getPaidAmount());
            paymentDTO.setSourceExchangeRate(BigDecimal.valueOf(1.0));
            
            // if ("Receive".equals(paymentDTO.getPaymentType())) {
                
            //     paymentDTO.setPaidAmount(null);
            // }
            System.out.println();
            System.out.println(paymentDTO.getInvoiceName());
            System.out.println();
            String paymentResult = paymentService.processPayment(paymentDTO);
            if (paymentDTO.getPaidAmount() == null || paymentDTO.getPaidAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant payé doit être supérieur à zéro");
            }
             
            redirectAttributes.addFlashAttribute("success", "Paiement enregistré avec succès");
            return "redirect:/payments/success?invoice=" + paymentDTO.getInvoiceName();
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/payments?facture=" + paymentDTO.getInvoiceName();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du paiement: " + e.getMessage());
            return "redirect:/payments?facture=" + paymentDTO.getInvoiceName();
        }
    }

    @GetMapping("/success")
    public ModelAndView paymentSuccess(@RequestParam String invoice) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "invoices/payment_success");
        modelAndView.addObject("invoiceNumber", invoice);
        return modelAndView;
    }
}