package com.project.hackathon.dto;

import java.util.Date;

public class PredictCostPerImpsRequest {

    private Integer supplierId;
    private String supplierName;
    private Date startDate;
    private Date endDate;
    private String advertiser;
    private String product;

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

    @Override
    public String toString() {
        return "PredictCostPerImpsRequest{" + "supplierId=" + supplierId + ", supplierName='" + supplierName + '\'' + ", startDate=" + startDate + ", endDate=" + endDate
                + ", advertiser='" + advertiser + '\'' + ", product='" + product + '\'' + '}';
    }
}
