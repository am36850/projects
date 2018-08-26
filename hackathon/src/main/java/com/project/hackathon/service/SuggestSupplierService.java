package com.project.hackathon.service;

import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;

import java.util.List;

public interface SuggestSupplierService {

    List<SuggestSupplierResponse> suggestSupplier(SuggestSupplierRequest suggestSupplierRequest);
}
