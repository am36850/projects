package com.project.hackathon.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class SupplierResponse implements Comparable<SupplierResponse>{

    private Integer supplierId;
    private String supplierName;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date endDate;
    private BigDecimal targetedBudget;
    private Integer targetedImpressions;
    private BigDecimal predictedBudget;
    private Integer predictedImpressions;
    private Double confidencePercentage;
    private Integer rating;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTargetedBudget() {
        return targetedBudget;
    }

    public void setTargetedBudget(BigDecimal targetedBudget) {
        this.targetedBudget = targetedBudget;
    }

    public Integer getTargetedImpressions() {
        return targetedImpressions;
    }

    public void setTargetedImpressions(Integer targetedImpressions) {
        this.targetedImpressions = targetedImpressions;
    }

    public BigDecimal getPredictedBudget() {
        return predictedBudget;
    }

    public void setPredictedBudget(BigDecimal predictedBudget) {
        this.predictedBudget = predictedBudget;
    }

    public Integer getPredictedImpressions() {
        return predictedImpressions;
    }

    public void setPredictedImpressions(Integer predictedImpressions) {
        this.predictedImpressions = predictedImpressions;
    }

    public Double getConfidencePercentage() {
        return confidencePercentage;
    }

    public void setConfidencePercentage(Double confidencePercentage) {
        this.confidencePercentage = confidencePercentage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "SupplierResponse{" + "supplierId=" + supplierId + ", supplierName='" + supplierName + '\'' + ", startDate=" + startDate + ", endDate=" + endDate
                + ", targetedBudget=" + targetedBudget + ", targetedImpressions=" + targetedImpressions + ", predictedBudget=" + predictedBudget + ", predictedImpressions="
                + predictedImpressions + ", confidencePercentage=" + confidencePercentage + ", rating=" + rating + '}';
    }

    @Override
    public int compareTo(SupplierResponse o) {
        return o.getConfidencePercentage().compareTo(this.getConfidencePercentage());
    }
}
