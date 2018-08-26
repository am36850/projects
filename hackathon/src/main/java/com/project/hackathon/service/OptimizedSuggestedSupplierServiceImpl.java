package com.project.hackathon.service;

import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OptimizedSuggestedSupplierServiceImpl implements OptimizeSuggestedSuppliersService{

    @Override
    public List<SuggestSupplierResponse> optimizeSuggestedSupplier(SuggestSupplierRequest suggestSupplierRequest, List<SuggestSupplierResponse> suggestSuppliers) {
        Collections.sort(suggestSuppliers);
        return suggestSuppliers;
    }
}
