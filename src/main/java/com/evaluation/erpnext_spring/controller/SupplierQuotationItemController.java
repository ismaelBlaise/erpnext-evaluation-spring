package com.evaluation.erpnext_spring.controller;

import com.evaluation.erpnext_spring.dto.SupplierQuotationItemDto;
import com.evaluation.erpnext_spring.dto.SupplierQuotationGroupDto;
import com.evaluation.erpnext_spring.dto.SupplierQuotationItemListResponse;
import com.evaluation.erpnext_spring.service.SupplierQuotationItemService;
import com.evaluation.erpnext_spring.service.SupplierQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/quotation-items")
public class SupplierQuotationItemController {

    @Autowired
    private SupplierQuotationItemService supplierQuotationService;

    @GetMapping
    public ModelAndView getSupplierQuotationItems(HttpSession session,
                                                  @RequestParam String quotationId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "quotations/items");

        try {
            SupplierQuotationItemListResponse response = supplierQuotationService.getSupplierQuotation(session, quotationId);

            
            SupplierQuotationGroupDto quotationGroup = response.getData();

            List<SupplierQuotationItemDto> items = quotationGroup != null ? quotationGroup.getItems() : null;

            modelAndView.addObject("quotationItems", items);
            modelAndView.addObject("quotationInfo", quotationGroup);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("pageSize", size);
            modelAndView.addObject("quotationId", quotationId);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "Erreur lors de la récupération des items du devis : " + e.getMessage());
            modelAndView.addObject("page", "error");
        }

        return modelAndView;
    }
}
