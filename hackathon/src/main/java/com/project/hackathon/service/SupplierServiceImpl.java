package com.project.hackathon.service;

import com.project.hackathon.domain.SupplierMaster;
import com.project.hackathon.repository.SupplierMasterRepository;
import com.project.hackathon.web.request.MLRequest;
import com.project.hackathon.web.request.SupplierRequest;
import com.project.hackathon.web.response.MLResponse;
import com.project.hackathon.web.response.SupplierResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMasterRepository supplierMasterRepository;

    private final MachineLearningService machineLearningService;

    @Autowired
    public SupplierServiceImpl(SupplierMasterRepository supplierMasterRepository, MachineLearningService machineLearningService) {
        this.supplierMasterRepository = supplierMasterRepository;
        this.machineLearningService = machineLearningService;
    }

    @Override
    public List<SupplierResponse> fetchSupplierResponse(SupplierRequest supplierRequest) {
        List<SupplierResponse> responses = new ArrayList<>();
        List<SupplierMaster> supplierMasters = supplierMasterRepository.findByAdvertiserNameOrProductName(supplierRequest.getAdvertiser(), supplierRequest.getProduct());
        if (!CollectionUtils.isEmpty(supplierMasters)) {
            for (SupplierMaster supplierMaster : supplierMasters) {
                MLResponse mlResponse = machineLearningService.predictImpressionAndCostPerImps(generateMLRequest(supplierMaster, supplierRequest));
                SupplierResponse supplierResponse = prepareSupplierResponse(supplierMaster, supplierRequest, mlResponse);
                responses.add(supplierResponse);
            }
        }
        Collections.sort(responses);
        return responses;
    }

    private SupplierResponse prepareSupplierResponse(SupplierMaster supplierMaster, SupplierRequest supplierRequest, MLResponse mlResponse) {
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setStartDate(supplierRequest.getStartDate());
        supplierResponse.setEndDate(supplierRequest.getEndDate());
        supplierResponse.setSupplierId(supplierMaster.getId());
        supplierResponse.setSupplierName(supplierMaster.getSupplierName());
        supplierResponse.setPredictedBudget(calculatePredictedBudget(supplierRequest, mlResponse));
        supplierResponse.setPredictedImpressions(calculatePredictedImpressions(mlResponse, supplierRequest));
        supplierResponse.setTargetedBudget(supplierRequest.getTargetedBudget());
        supplierResponse.setTargetedImpressions(supplierRequest.getTargetedImpressions());
        supplierResponse.setRating(getRatings(mlResponse.getConfidencePercentage()));
        supplierResponse.setConfidencePercentage(mlResponse.getConfidencePercentage());
        return supplierResponse;
    }

    private Integer getRatings(Double confidencePercentage) {
        int ratings = 5;
        if (confidencePercentage < 1) {
            confidencePercentage = confidencePercentage - 0.50;
            ratings = (int) Math.ceil(confidencePercentage * 10);
        }
        return ratings;
    }

    private Integer calculatePredictedImpressions(MLResponse mlResponse, SupplierRequest supplierRequest) {
        int duration = calculateDuration(supplierRequest.getStartDate(), supplierRequest.getEndDate());
        return mlResponse.getPredicatedImpressionsPerDay() * duration;
    }

    private BigDecimal calculatePredictedBudget(SupplierRequest supplierRequest, MLResponse mlResponse) {
        Double budgetPerDay = mlResponse.getCostPerImpression() * mlResponse.getPredicatedImpressionsPerDay();
        long durationOfCampaign = calculateDuration(supplierRequest.getStartDate(), supplierRequest.getEndDate());
        double predicatedBudget = budgetPerDay * durationOfCampaign;
        return BigDecimal.valueOf(predicatedBudget);
    }

    private int calculateDuration(Date firstDate, Date secondDate) {
        if (firstDate.getTime() == secondDate.getTime()) {
            return 1;
        }
        long diffInMollies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return Math.toIntExact(TimeUnit.DAYS.convert(diffInMollies, TimeUnit.MILLISECONDS));
    }

    private MLRequest generateMLRequest(SupplierMaster supplierMaster, SupplierRequest supplierRequest) {
        MLRequest mlRequest = new MLRequest();
        mlRequest.setEndDate(supplierRequest.getEndDate());
        mlRequest.setStartDate(supplierRequest.getStartDate());
        mlRequest.setSupplierName(supplierMaster.getSupplierName());
        return mlRequest;
    }

}
