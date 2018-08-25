package com.project.hackathon.service;

import com.project.hackathon.web.request.MLRequest;
import com.project.hackathon.web.response.MLResponse;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MachineLearningServiceImpl implements MachineLearningService{

    @Override
    public MLResponse predictImpressionAndCostPerImps(MLRequest mlRequest) {
        return generateDummyResponse();
    }

    private MLResponse generateDummyResponse() {
        Random random = new Random();
        MLResponse mlResponse = new MLResponse();
        mlResponse.setCostPerImpression(.75*(random.nextInt(70)+1));
        mlResponse.setPredicatedImpressionsPerDay(random.nextInt(2555)+99);
        mlResponse.setConfidencePercentage(Math.round((random.nextInt(99)/100d))+0.55);
        return mlResponse;
    }
}
