package com.project.hackathon.web.response;

public class MLResponse {

    private Integer predicatedImpressionsPerDay;
    private Double costPerImpression;
    private Double confidencePercentage;

    public Integer getPredicatedImpressionsPerDay() {
        return predicatedImpressionsPerDay;
    }

    public void setPredicatedImpressionsPerDay(Integer predicatedImpressionsPerDay) {
        this.predicatedImpressionsPerDay = predicatedImpressionsPerDay;
    }

    public Double getCostPerImpression() {
        return costPerImpression;
    }

    public void setCostPerImpression(Double costPerImpression) {
        this.costPerImpression = costPerImpression;
    }

    public Double getConfidencePercentage() {
        return confidencePercentage;
    }

    public void setConfidencePercentage(Double confidencePercentage) {
        this.confidencePercentage = confidencePercentage;
    }
}
