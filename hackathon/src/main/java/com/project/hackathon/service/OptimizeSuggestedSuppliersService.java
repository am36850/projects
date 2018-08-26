package com.project.hackathon.service;

import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;

import java.util.List;

public interface OptimizeSuggestedSuppliersService {

    List<SuggestSupplierResponse> optimizeSuggestedSupplier(SuggestSupplierRequest suggestSupplierRequest,List<SuggestSupplierResponse> suggestSuppliers);
}
