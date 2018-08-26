package com.project.hackathon.service;

import com.project.hackathon.dto.CompletionRatioRequest;
import com.project.hackathon.dto.CompletionRatioResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class PredictCompletionRationWatsonML implements PredictCompletionRatioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictCompletionRationWatsonML.class);

    @Override
    @Async
    public CompletableFuture<CompletionRatioResponse> predictCompletionRation(CompletionRatioRequest completionRationRequest) {
        LOGGER.info("Request Received for Completion Ration Prediction Service {}", completionRationRequest);
        return CompletableFuture.completedFuture(getDummyCompletionRation(completionRationRequest));
    }

    private CompletionRatioResponse getDummyCompletionRation(CompletionRatioRequest completionRatioRequest) {
        CompletionRatioResponse completionRatioResponse = new CompletionRatioResponse();
        completionRatioResponse.setAdvertiser(completionRatioRequest.getAdvertiser());
        completionRatioResponse.setProduct(completionRatioRequest.getProduct());
        completionRatioResponse.setStartDate(completionRatioRequest.getStartDate());
        completionRatioResponse.setEndDate(completionRatioRequest.getEndDate());
        completionRatioResponse.setSupplierName(completionRatioRequest.getSupplierName());
        completionRatioResponse.setDuration(completionRatioRequest.getDuration());
        completionRatioResponse.setTotalBudget(completionRatioRequest.getTotalBudget());
        completionRatioResponse.setTotalImpression(completionRatioRequest.getTotalImpression());
        completionRatioResponse.setCompletionRatio(generateRandomCompletionRation());
        return completionRatioResponse;
    }

    private Double generateRandomCompletionRation() {
        Random random = new Random(1);
        return random.nextDouble() + 0.55;
    }
}
