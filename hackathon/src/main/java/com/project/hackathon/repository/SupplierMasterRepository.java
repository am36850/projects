package com.project.hackathon.repository;

import com.project.hackathon.domain.SupplierMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierMasterRepository extends JpaRepository<SupplierMaster,Integer> {

    @Query(value = "select e from SupplierMaster e where (e.advertiserName =:advertiserName and e.productName=:productName) or (e.productName=:productName) or (e.advertiserName=:advertiserName) order by e.id")
    List<SupplierMaster> findByAdvertiserNameOrProductName(@Param("advertiserName") String advertiserName,@Param("productName") String productName);
}
