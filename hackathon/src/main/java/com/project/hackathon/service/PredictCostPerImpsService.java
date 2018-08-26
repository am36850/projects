package com.project.hackathon.service;

import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;
import com.project.hackathon.dto.PredictCostPerImpsRequest;

import java.util.concurrent.CompletableFuture;

public interface PredictCostPerImpsService {

    CompletableFuture<ImpressionAndCostPredictionResponse> predictCostPerImps(PredictCostPerImpsRequest predictCostPerImpsRequest);
}
