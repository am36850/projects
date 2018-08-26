package com.project.hackathon.repository;

import com.project.hackathon.domain.SupplierMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierMasterRepository extends JpaRepository<SupplierMaster,Integer> {

    @Query(value = "select e from SupplierMaster e where (lower(e.advertiserName) =:advertiserName and lower(e.productName)=:productName) or (lower(e.productName)=:productName) or (lower(e.advertiserName)=:advertiserName)")
    List<SupplierMaster> findByAdvertiserNameOrProductName(@Param("advertiserName") String advertiserName,@Param("productName") String productName);
}
