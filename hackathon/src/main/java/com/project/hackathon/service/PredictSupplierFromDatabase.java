package com.project.hackathon.service;

import com.project.hackathon.domain.SupplierMaster;
import com.project.hackathon.dto.PredictSupplierRequest;
import com.project.hackathon.dto.PredictSupplierResponse;
import com.project.hackathon.repository.SupplierMasterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component("predictSupplierFromDatabase")
public class PredictSupplierFromDatabase implements PredictSupplier {

    private final SupplierMasterRepository supplierMasterRepository;

    @Autowired
    public PredictSupplierFromDatabase(SupplierMasterRepository supplierMasterRepository) {
        this.supplierMasterRepository = supplierMasterRepository;
    }

    @Override
    public List<PredictSupplierResponse> predictSupplier(PredictSupplierRequest predictSupplierRequest) {
        List<SupplierMaster> supplierMasters = supplierMasterRepository.findByAdvertiserNameOrProductName(predictSupplierRequest.getAdvertiser().toLowerCase(), predictSupplierRequest.getProduct().toLowerCase());
        List<PredictSupplierResponse> predictSupplierResponses = new ArrayList<>(supplierMasters.size());
        if(!CollectionUtils.isEmpty(supplierMasters)){
            for(SupplierMaster supplierMaster : supplierMasters){
                PredictSupplierResponse predictSupplierResponse = new PredictSupplierResponse();
                predictSupplierResponse.setSupplierId(supplierMaster.getId());
                predictSupplierResponse.setSupplierName(supplierMaster.getSupplierName());
                predictSupplierResponses.add(predictSupplierResponse);
            }
        }
        return predictSupplierResponses;
    }
}
