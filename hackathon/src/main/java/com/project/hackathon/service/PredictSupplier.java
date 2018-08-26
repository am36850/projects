package com.project.hackathon.service;

import com.project.hackathon.dto.PredictSupplierRequest;
import com.project.hackathon.dto.PredictSupplierResponse;

import java.util.List;

public interface PredictSupplier {

    List<PredictSupplierResponse> predictSupplier(PredictSupplierRequest predictSupplierRequest);
}
