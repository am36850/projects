package com.project.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class SuggestSupplierRequest {

    private String supplierName;
    private String advertiser;
    private String product;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date endDate;
    private BigDecimal targetedBudget;
    private Integer targetedImpressions;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
}
