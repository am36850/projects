package com.project.hackathon.dto;

public class PredictSupplierRequest {

    private String advertiser;
    private String product;

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
        return "PredictSupplierRequest{" + "advertiser='" + advertiser + '\'' + ", product='" + product + '\'' + '}';
    }
}
