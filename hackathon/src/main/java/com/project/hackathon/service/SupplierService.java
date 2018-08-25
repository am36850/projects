package com.project.hackathon.service;

import com.project.hackathon.web.request.SupplierRequest;
import com.project.hackathon.web.response.SupplierResponse;

import java.util.List;

public interface SupplierService {

    List<SupplierResponse> fetchSupplierResponse(SupplierRequest supplierRequest);
}
