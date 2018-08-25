package com.project.sharemarket.repository;

import com.project.sharemarket.domains.NsePerDayData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NsePerDayDataRepository extends JpaRepository<NsePerDayData,Integer> {
}
