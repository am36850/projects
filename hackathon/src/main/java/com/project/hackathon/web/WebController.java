package com.project.hackathon.web;

import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;
import com.project.hackathon.service.NotificationService;
import com.project.hackathon.service.SuggestSupplierService;
import com.project.hackathon.service.SupplierService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebController {

    private static Logger logger = LoggerFactory.getLogger(WebController.class);

    private final SupplierService supplierService;

    private final SuggestSupplierService suggestSupplierService;

    private final NotificationService notificationService;

    @Autowired
    public WebController(SupplierService supplierService, SuggestSupplierService suggestSupplierService, NotificationService notificationService) {
        this.supplierService = supplierService;
        this.suggestSupplierService = suggestSupplierService;
        this.notificationService = notificationService;
    }

    /*@PutMapping(value = "/getsuppliers")
    @CrossOrigin
    public List<SupplierResponse> getSupplierResponse(@RequestBody SupplierRequest supplierRequest){
        logger.info("Request received for {}",supplierRequest);
        List<SupplierResponse> responses = supplierService.fetchSupplierResponse(supplierRequest);
        logger.info("Response Send {}",responses);
        return responses;
    }*/

    @PutMapping(value = "/getsuppliers")
    @CrossOrigin
    public List<SuggestSupplierResponse> postSupplierResponse(@RequestBody SuggestSupplierRequest supplierRequest){
        logger.info("Request received for {}",supplierRequest);
        List<SuggestSupplierResponse> responses = suggestSupplierService.suggestSupplier(supplierRequest);
        logger.info("Response Send {}",responses);
        notificationService.progress("5");
        return responses;
    }
}
