package com.project.hackathon.service;

import com.project.hackathon.web.request.MLRequest;
import com.project.hackathon.web.response.MLResponse;

public interface MachineLearningService {

    MLResponse predictImpressionAndCostPerImps(MLRequest mlRequest);
}
