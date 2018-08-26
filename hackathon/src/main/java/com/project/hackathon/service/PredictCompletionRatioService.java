package com.project.hackathon.service;

import com.project.hackathon.dto.CompletionRatioRequest;
import com.project.hackathon.dto.CompletionRatioResponse;

import java.util.concurrent.CompletableFuture;

public interface PredictCompletionRatioService {

    CompletableFuture<CompletionRatioResponse> predictCompletionRation(CompletionRatioRequest completionRationRequest);
}
