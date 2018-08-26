package com.project.hackathon.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CompletionRatioRequest {

    private String supplierName;
    private Date startDate;
    private Date endDate;
    private String advertiser;
    private String product;
    private Integer duration;
    private Integer totalImpression;
    private BigDecimal totalBudget;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalImpression() {
        return totalImpression;
    }

    public void setTotalImpression(Integer totalImpression) {
        this.totalImpression = totalImpression;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }
}
