package com.project.hackathon.service;

import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;
import com.project.hackathon.dto.PredictCostPerImpsRequest;
import com.project.hackathon.dto.PredictCostPerImpsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class PredictCostPerImpsWatsonML implements PredictCostPerImpsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictCostPerImpsWatsonML.class);

    @Override
    @Async
    public CompletableFuture<ImpressionAndCostPredictionResponse> predictCostPerImps(PredictCostPerImpsRequest predictCostPerImpsRequest) {
        LOGGER.info("Request Received for Cost Per Impression Prediction Service {}", predictCostPerImpsRequest);
        return CompletableFuture.completedFuture(getDummyCostPerImpsData(predictCostPerImpsRequest));
    }

    private ImpressionAndCostPredictionResponse getDummyCostPerImpsData(PredictCostPerImpsRequest predictCostPerImpsRequest) {
        ImpressionAndCostPredictionResponse impressionAndCostPredictionResponse = new ImpressionAndCostPredictionResponse();
        impressionAndCostPredictionResponse.setAdvertiser(predictCostPerImpsRequest.getAdvertiser());
        impressionAndCostPredictionResponse.setStartDate(predictCostPerImpsRequest.getStartDate());
        impressionAndCostPredictionResponse.setEndDate(predictCostPerImpsRequest.getEndDate());
        impressionAndCostPredictionResponse.setProduct(predictCostPerImpsRequest.getProduct());
        impressionAndCostPredictionResponse.setSupplierName(predictCostPerImpsRequest.getSupplierName());
        impressionAndCostPredictionResponse.setCostPerImps(generateRandomCostPerImps());
        return impressionAndCostPredictionResponse;
    }

    private Double generateRandomCostPerImps() {
        Random random = new Random(5);
        return random.nextDouble();
    }
}
