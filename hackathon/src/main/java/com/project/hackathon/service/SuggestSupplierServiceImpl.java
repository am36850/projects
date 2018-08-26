package com.project.hackathon.service;

import com.project.hackathon.dto.CompletionRatioResponse;
import com.project.hackathon.dto.ImpressionAndCostPredictionResponse;
import com.project.hackathon.dto.ImpressionPerDayRequest;
import com.project.hackathon.dto.PredictCostPerImpsRequest;
import com.project.hackathon.dto.PredictSupplierRequest;
import com.project.hackathon.dto.PredictSupplierResponse;
import com.project.hackathon.dto.SuggestSupplierRequest;
import com.project.hackathon.dto.SuggestSupplierResponse;

import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class SuggestSupplierServiceImpl implements SuggestSupplierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestSupplierService.class);

    private final PredictSupplier predictSupplier;

    private final PredictImpressionsPerDayService predictImpressionsPerDayService;

    private final PredictCostPerImpsService predictCostPerImpsService;

    private final PredictCompletionRatioService predictCompletionRatioService;

    private final OptimizeSuggestedSuppliersService optimizeSuggestedSuppliersService;

    @Autowired
    public SuggestSupplierServiceImpl(PredictSupplier predictSupplier, PredictImpressionsPerDayService predictImpressionsPerDayService,
            PredictCostPerImpsService predictCostPerImpsService, PredictCompletionRatioService predictCompletionRatioService,
            OptimizeSuggestedSuppliersService optimizeSuggestedSuppliersService) {
        this.predictSupplier = predictSupplier;
        this.predictImpressionsPerDayService = predictImpressionsPerDayService;
        this.predictCostPerImpsService = predictCostPerImpsService;
        this.predictCompletionRatioService = predictCompletionRatioService;
        this.optimizeSuggestedSuppliersService = optimizeSuggestedSuppliersService;
    }

    @Override
    public List<SuggestSupplierResponse> suggestSupplier(SuggestSupplierRequest suggestSupplierRequest) {

        //step 1: predicting suppliers based on advertiser and product
        List<PredictSupplierResponse> predictedSuppliers = predictSupplier.predictSupplier(mapPredictSupplierRequest(suggestSupplierRequest));

        // step2 : predicting Impressions per day for predicted suppliers in async fashion
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureImpsPerDay = submitImpsPerDayRequest(predictedSuppliers,suggestSupplierRequest);

        // step3 : predicting Cost per Impression for predicted suppliers in async fashion
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> completableFutureCostPerDay = submitCostPerDayRequest(predictedSuppliers,suggestSupplierRequest);

        // waiting for asynchronous tasks to finish
        List<CompletableFuture<ImpressionAndCostPredictionResponse>> allCompletableFutures = completableFutureImpsPerDay;
        allCompletableFutures.addAll(completableFutureCostPerDay);
        List<ImpressionAndCostPredictionResponse> impressionAndCostPredictionResponses = allCompletableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        // merging predicted responses from step 2 and step 3
        List<ImpressionAndCostPredictionResponse> predictedImpsAndCostValues = filterResult(impressionAndCostPredictionResponses,predictedSuppliers);

        //step 4 : predicting completion ratio for predicted suppliers
        List<CompletableFuture<CompletionRatioResponse>> completableFutures = submitCompletionRationRequest(predictedImpsAndCostValues,suggestSupplierRequest);
        List<CompletionRatioResponse> completionRatioResponses = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        //step 5 : optimizing suggested suppliers response as per request
        return submitOptimizedSupplierRequest(completionRatioResponses);
    }


    **************
    private List<SuggestSupplierResponse> submitOptimizedSupplierRequest(List<CompletionRatioResponse> completionRatioResponses) {
        throw new NotYetImplementedException();
    }

    **************
    private List<CompletableFuture<CompletionRatioResponse>> submitCompletionRationRequest(List<ImpressionAndCostPredictionResponse> predictedImpsAndCostValues, SuggestSupplierRequest suggestSupplierRequest) {
        throw new NotYetImplementedException();
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
