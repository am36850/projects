package com.project.hackathon.service;

import com.project.hackathon.dto.ImpressionPerDayRequest;
import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class PredictImpsPerDayWatsonML implements PredictImpressionsPerDayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictImpsPerDayWatsonML.class);

    @Override
    @Async
    public CompletableFuture<ImpressionAndCostPredictionResponse> predictImpsPerDay(ImpressionPerDayRequest impressionPerDayRequest) {
        LOGGER.info("Request Received for Impression Prediction Service {}",impressionPerDayRequest);
        return CompletableFuture.completedFuture(getDummyImpressionData(impressionPerDayRequest));
    }

    private ImpressionAndCostPredictionResponse getDummyImpressionData(ImpressionPerDayRequest impressionPerDayRequest) {
        ImpressionAndCostPredictionResponse impressionPerDayResponse = new ImpressionAndCostPredictionResponse();
        impressionPerDayResponse.setAdvertiser(impressionPerDayRequest.getAdvertiser());
        impressionPerDayResponse.setStartDate(impressionPerDayRequest.getStartDate());
        impressionPerDayResponse.setEndDate(impressionPerDayRequest.getEndDate());
        impressionPerDayResponse.setProduct(impressionPerDayRequest.getProduct());
        impressionPerDayResponse.setSupplierId(impressionPerDayRequest.getSupplierId());
        impressionPerDayResponse.setSupplierName(impressionPerDayRequest.getSupplierName());
        impressionPerDayResponse.setImpsPerDay(generateRandomImpression());
        return impressionPerDayResponse;
    }

    private Integer generateRandomImpression() {
        Random random = new Random(4000);
        return random.nextInt() & Integer.MAX_VALUE;
    }
}
