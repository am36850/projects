package com.project.hackathon.service;

import com.project.hackathon.dto.CompletionRatioRequest;
import com.project.hackathon.dto.CompletionRatioResponse;
import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;
import com.project.hackathon.dto.ImpressionPerDayRequest;
import com.project.hackathon.dto.PredictCostPerImpsRequest;
import com.project.hackathon.dto.PredictSupplierRequest;
import com.project.hackathon.dto.PredictSupplierResponse;
import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SuggestSupplierServiceImpl implements SuggestSupplierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestSupplierService.class);

    private final PredictSupplier predictSupplier;

    private final PredictImpressionsPerDayService predictImpressionsPerDayService;

    private final PredictCostPerImpsService predictCostPerImpsService;

    private final PredictCompletionRatioService predictCompletionRatioService;

    private final OptimizeSuggestedSuppliersService optimizeSuggestedSuppliersService;

    private final NotificationService notificationService;

    @Autowired
    public SuggestSupplierServiceImpl(PredictSupplier predictSupplier, PredictImpressionsPerDayService predictImpressionsPerDayService,
            PredictCostPerImpsService predictCostPerImpsService, PredictCompletionRatioService predictCompletionRatioService,
            OptimizeSuggestedSuppliersService optimizeSuggestedSuppliersService, NotificationService notificationService) {
        this.predictSupplier = predictSupplier;
        this.predictImpressionsPerDayService = predictImpressionsPerDayService;
        this.predictCostPerImpsService = predictCostPerImpsService;
        this.predictCompletionRatioService = predictCompletionRatioService;
        this.optimizeSuggestedSuppliersService = optimizeSuggestedSuppliersService;
        this.notificationService = notificationService;
    }

    @Override
    public List<SuggestSupplierResponse> suggestSupplier(SuggestSupplierRequest suggestSupplierRequest) {

        LOGGER.info("Request received {}",suggestSupplierRequest);

        //step 1: predicting suppliers based on advertiser and product
        List<PredictSupplierResponse> predictedSuppliers = predictSupplier.predictSupplier(mapPredictSupplierRequest(suggestSupplierRequest));

        notificationService.progress("1");

        // step2 : predicting Impressions per day for predicted suppliers in async fashion
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureImpsPerDay = submitImpsPerDayRequest(predictedSuppliers,suggestSupplierRequest);

        // step3 : predicting Cost per Impression for predicted suppliers in async fashion
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureCostPerDay = submitCostPerDayRequest(predictedSuppliers,suggestSupplierRequest);

        // waiting for asynchronous tasks to finish
        completableFutureImpsPerDay.addAll(completableFutureCostPerDay);
        List<ImpressionAndCostPredictionResponse> impressionAndCostPredictionResponses = completableFutureImpsPerDay.stream().map(CompletableFuture::join).collect(Collectors.toList());

        // merging predicted responses from step 2 and step 3
        List<ImpressionAndCostPredictionResponse> predictedImpsAndCostValues = filterResult(impressionAndCostPredictionResponses,predictedSuppliers);

        notificationService.progress("2");

        //step 4 : predicting completion ratio for predicted suppliers
        List<CompletableFuture<CompletionRatioResponse>> completableFutures = submitCompletionRationRequest(predictedImpsAndCostValues);
        List<CompletionRatioResponse> completionRatioResponses = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        notificationService.progress("3");

        //step 5 : optimizing suggested suppliers response as per request
        List<SuggestSupplierResponse> suggestSupplierResponses = submitOptimizedSupplierRequest(suggestSupplierRequest,completionRatioResponses);

        notificationService.progress("4");

        return suggestSupplierResponses;
    }

    private List<SuggestSupplierResponse> submitOptimizedSupplierRequest(SuggestSupplierRequest suggestSupplierRequest, List<CompletionRatioResponse> completionRatioResponses) {
        List<SuggestSupplierResponse> suggestSupplierResponses = new ArrayList<>();
        for (CompletionRatioResponse completionRatioResponse : completionRatioResponses){
            SuggestSupplierResponse supplierResponse = new SuggestSupplierResponse();
            supplierResponse.setStartDate(completionRatioResponse.getStartDate());
            supplierResponse.setEndDate(completionRatioResponse.getEndDate());
            supplierResponse.setSupplierId(completionRatioResponse.getSupplierId());
            supplierResponse.setSupplierName(completionRatioResponse.getSupplierName());
            supplierResponse.setTargetedImpressions(suggestSupplierRequest.getTargetedImpressions());
            supplierResponse.setTargetedBudget(suggestSupplierRequest.getTargetedBudget());
            supplierResponse.setConfidencePercentage(completionRatioResponse.getCompletionRatio());
            supplierResponse.setPredictedBudget(completionRatioResponse.getTotalBudget());
            supplierResponse.setPredictedImpressions(completionRatioResponse.getTotalImpression());
            supplierResponse.setRating(getRatings(completionRatioResponse.getCompletionRatio()));
            suggestSupplierResponses.add(supplierResponse);
        }
        return optimizeSuggestedSuppliersService.optimizeSuggestedSupplier(suggestSupplierRequest,suggestSupplierResponses);
    }

    private List<CompletableFuture<CompletionRatioResponse>> submitCompletionRationRequest(List<ImpressionAndCostPredictionResponse> predictedImpsAndCostValues) {
        List<CompletionRatioRequest> completionRatioRequests = new ArrayList<>();
        List<CompletableFuture<CompletionRatioResponse>> completableFutures = new ArrayList<>();
        for(ImpressionAndCostPredictionResponse impressionAndCostPredictionResponse : predictedImpsAndCostValues) {
            Integer predictedImpressions = calculatePredictedImpressions(impressionAndCostPredictionResponse.getImpsPerDay(),impressionAndCostPredictionResponse.getStartDate(),impressionAndCostPredictionResponse.getEndDate());
            CompletionRatioRequest completionRatioRequest = new CompletionRatioRequest();
            completionRatioRequest.setAdvertiser(impressionAndCostPredictionResponse.getAdvertiser());
            completionRatioRequest.setProduct(impressionAndCostPredictionResponse.getProduct());
            completionRatioRequest.setStartDate(impressionAndCostPredictionResponse.getStartDate());
            completionRatioRequest.setEndDate(impressionAndCostPredictionResponse.getEndDate());
            completionRatioRequest.setDuration(calculateDuration(impressionAndCostPredictionResponse.getStartDate(),impressionAndCostPredictionResponse.getEndDate()));
            completionRatioRequest.setSupplierId(impressionAndCostPredictionResponse.getSupplierId());
            completionRatioRequest.setSupplierName(impressionAndCostPredictionResponse.getSupplierName());
            completionRatioRequest.setTotalImpression(predictedImpressions);
            completionRatioRequest.setTotalBudget(calculatePredictedBudget(predictedImpressions,impressionAndCostPredictionResponse.getCostPerImps()));
            completionRatioRequests.add(completionRatioRequest);
        }
        for(CompletionRatioRequest completionRatioRequest : completionRatioRequests) {
            completableFutures.add(predictCompletionRatioService.predictCompletionRation(completionRatioRequest));
        }
        return completableFutures;
    }

    private BigDecimal calculatePredictedBudget(Integer predictedImpressions, Double costPerImps) {
        double predicatedBudget = predictedImpressions * costPerImps;
        return BigDecimal.valueOf(Math.round(predicatedBudget*100.0)/100);
    }

    private Integer calculatePredictedImpressions(Integer impsPerDay, Date startDate, Date endDate) {
        int duration = calculateDuration(startDate, endDate);
        return impsPerDay * duration;
    }

    private Integer getRatings(Double confidencePercentage) {
        int ratings = 5;
        if (confidencePercentage < 1) {
            confidencePercentage = confidencePercentage - 0.50;
            ratings = (int) Math.ceil(confidencePercentage * 10);
        }
        return ratings;
    }

    private int calculateDuration(Date firstDate, Date secondDate) {
        if (firstDate.getTime() == secondDate.getTime()) {
            return 1;
        }
        long diffInMollies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return Math.toIntExact(TimeUnit.DAYS.convert(diffInMollies, TimeUnit.MILLISECONDS));
    }

    private List<ImpressionAndCostPredictionResponse> filterResult(List<ImpressionAndCostPredictionResponse> impressionAndCostPredictionResponses,
            List<PredictSupplierResponse> predictedSuppliers) {
        List<ImpressionAndCostPredictionResponse> responses = new ArrayList<>();
        for (PredictSupplierResponse predictSupplierResponse : predictedSuppliers) {
            List<ImpressionAndCostPredictionResponse> filteredBySupplierId = impressionAndCostPredictionResponses.stream().filter(p -> p.getSupplierId().compareTo(predictSupplierResponse.getSupplierId())==0).collect(Collectors.toList());
            ImpressionAndCostPredictionResponse mergedResponse = ImpressionAndCostPredictionResponse.mergeTwoResponse(filteredBySupplierId.get(0),filteredBySupplierId.get(1));
            responses.add(mergedResponse);
        }
        return responses;
    }

    private List<CompletableFuture<ImpressionAndCostPredictionResponse>> submitCostPerDayRequest(List<PredictSupplierResponse> predictedSuppliers, SuggestSupplierRequest suggestSupplierRequest) {
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureList = new ArrayList<>();
        List<PredictCostPerImpsRequest> predictCostPerImpsRequests = new ArrayList<>();
        for(PredictSupplierResponse predictSupplierResponse : predictedSuppliers){
            PredictCostPerImpsRequest predictCostPerImpsRequest = new PredictCostPerImpsRequest();
            predictCostPerImpsRequest.setAdvertiser(suggestSupplierRequest.getAdvertiser());
            predictCostPerImpsRequest.setStartDate(suggestSupplierRequest.getStartDate());
            predictCostPerImpsRequest.setEndDate(suggestSupplierRequest.getEndDate());
            predictCostPerImpsRequest.setProduct(suggestSupplierRequest.getProduct());
            predictCostPerImpsRequest.setSupplierId(predictSupplierResponse.getSupplierId());
            predictCostPerImpsRequest.setSupplierName(predictSupplierResponse.getSupplierName());
            predictCostPerImpsRequests.add(predictCostPerImpsRequest);
        }
        for(PredictCostPerImpsRequest predictCostPerImpsRequest : predictCostPerImpsRequests){
            CompletableFuture<ImpressionAndCostPredictionResponse> responseCompletableFuture = predictCostPerImpsService.predictCostPerImps(predictCostPerImpsRequest);
            completableFutureList.add(responseCompletableFuture);
        }
        return completableFutureList;
    }

    private List<CompletableFuture<ImpressionAndCostPredictionResponse>> submitImpsPerDayRequest(List<PredictSupplierResponse> predictedSuppliers, SuggestSupplierRequest suggestSupplierRequest) {
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureList = new ArrayList<>();
        List<ImpressionPerDayRequest> impressionPerDayRequests = generateImpsPerDayRequests(predictedSuppliers,suggestSupplierRequest);
        for (ImpressionPerDayRequest impressionPerDayRequest : impressionPerDayRequests){
            CompletableFuture<ImpressionAndCostPredictionResponse> completableFuture = predictImpressionsPerDayService.predictImpsPerDay(impressionPerDayRequest);
            completableFutureList.add(completableFuture);
        }
        return completableFutureList;
    }

    private List<ImpressionPerDayRequest> generateImpsPerDayRequests(List<PredictSupplierResponse> predictedSuppliers, SuggestSupplierRequest suggestSupplierRequest) {
        List<ImpressionPerDayRequest> impressionPerDayRequests = new ArrayList<>();
        for(PredictSupplierResponse predictSupplierResponse : predictedSuppliers){
            ImpressionPerDayRequest impressionPerDayRequest = new ImpressionPerDayRequest();
            impressionPerDayRequest.setAdvertiser(suggestSupplierRequest.getAdvertiser());
            impressionPerDayRequest.setProduct(suggestSupplierRequest.getProduct());
            impressionPerDayRequest.setStartDate(suggestSupplierRequest.getStartDate());
            impressionPerDayRequest.setEndDate(suggestSupplierRequest.getEndDate());
            impressionPerDayRequest.setSupplierId(predictSupplierResponse.getSupplierId());
            impressionPerDayRequest.setSupplierName(predictSupplierResponse.getSupplierName());
            impressionPerDayRequests.add(impressionPerDayRequest);
        }
        return impressionPerDayRequests;
    }

    private PredictSupplierRequest mapPredictSupplierRequest(SuggestSupplierRequest suggestSupplierRequest) {
        PredictSupplierRequest predictSupplierRequest = new PredictSupplierRequest();
        predictSupplierRequest.setAdvertiser(suggestSupplierRequest.getAdvertiser());
        predictSupplierRequest.setProduct(suggestSupplierRequest.getProduct());
        return predictSupplierRequest;
    }
}
