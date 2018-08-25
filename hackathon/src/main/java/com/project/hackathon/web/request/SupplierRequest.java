package com.project.hackathon.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class SupplierRequest {

    private String supplierName;
    private String advertiser;
    private String product;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd-MMM-yyyy")
    private Date endDate;
    private BigDecimal targetedBudget;
    private Integer targetedImpressions;

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

    @Override
    public String toString() {
        return "SupplierRequest{" + "advertiser='" + advertiser + '\'' + ", product='" + product + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + ", targetedBudget="
                + targetedBudget + ", targetedImpressions=" + targetedImpressions + '}';
    }
}
