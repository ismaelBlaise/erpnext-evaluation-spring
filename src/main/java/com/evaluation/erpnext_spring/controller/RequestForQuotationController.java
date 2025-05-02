package com.evaluation.erpnext_spring.controller;

import com.evaluation.erpnext_spring.dto.quotations.RequestForQuotationListResponse;
import com.evaluation.erpnext_spring.dto.quotations.RequestForQuotationSupplierDTO;
import com.evaluation.erpnext_spring.service.RequestForQuotationService;
import com.evaluation.erpnext_spring.service.RequestForQuotationSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/request-quotations")
public class RequestForQuotationController {

    @Autowired
    private RequestForQuotationService requestForQuotationService;

    @Autowired
    private RequestForQuotationSupplierService requestForQuotationSupplierService;

    @GetMapping
    public ModelAndView getQuotationsBySupplier(
            HttpSession session,
            @RequestParam String supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "request-quotations/list");

        try {
            List<RequestForQuotationSupplierDTO> supplierDTOList = requestForQuotationSupplierService.getSuppliersBySupplierName(session, supplierId);

            RequestForQuotationListResponse response = requestForQuotationService.getQuotationsBySuppliers(session, supplierDTOList, page, size);

            modelAndView.addObject("quotations", response.getData());
            modelAndView.addObject("supplier", supplierId);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("pageSize", size);
           
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "Erreur lors de la récupération des Request for Quotations : " + e.getMessage());
            modelAndView.addObject("page", "error");
        }

        return modelAndView;
    }
}
