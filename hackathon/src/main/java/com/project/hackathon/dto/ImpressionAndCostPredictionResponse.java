package com.project.hackathon.dto;

import java.util.Date;

public class ImpressionAndCostPredictionResponse {

    private Integer supplierId;
    private String supplierName;
    private Date startDate;
    private Date endDate;
    private String advertiser;
    private String product;
    private Integer impsPerDay;
    private Double costPerImps;

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

    public Integer getImpsPerDay() {
        return impsPerDay;
    }

    public void setImpsPerDay(Integer impsPerDay) {
        this.impsPerDay = impsPerDay;
    }

    public Double getCostPerImps() {
        return costPerImps;
    }

    public void setCostPerImps(Double costPerImps) {
        this.costPerImps = costPerImps;
    }

    public static ImpressionAndCostPredictionResponse mergeTwoResponse(ImpressionAndCostPredictionResponse response1 , ImpressionAndCostPredictionResponse response2){
        if(response1.getCostPerImps()==null){
            response1.setCostPerImps(response2.getCostPerImps());
        }else if(response1.getImpsPerDay() ==null){
            response1.setImpsPerDay(response2.getImpsPerDay());
        }
        return response1;
    }

    @Override
    public String toString() {
        return "ImpressionAndCostPredictionResponse{" + "supplierId=" + supplierId + ", supplierName='" + supplierName + '\'' + ", startDate=" + startDate + ", endDate=" + endDate
                + ", advertiser='" + advertiser + '\'' + ", product='" + product + '\'' + ", impsPerDay=" + impsPerDay + ", costPerImps=" + costPerImps + '}';
    }
}
