package com.project.hackathon.service;

import com.project.hackathon.dto.ImpressionPerDayRequest;
import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;

import java.util.concurrent.CompletableFuture;

public interface PredictImpressionsPerDayService {

    CompletableFuture<ImpressionAndCostPredictionResponse> predictImpsPerDay(ImpressionPerDayRequest impressionPerDayRequest);
}
