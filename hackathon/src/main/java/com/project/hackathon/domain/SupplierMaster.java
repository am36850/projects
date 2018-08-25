package com.project.hackathon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "supplier_master", catalog = "hackathon", uniqueConstraints = @UniqueConstraint(columnNames = {
		"supplier_name", "advertiser_name", "product_name" }))
public class SupplierMaster implements java.io.Serializable {

	private Integer id;
	private Integer version;
	private String supplierName;
	private String advertiserName;
	private String productName;

	public SupplierMaster() {
	}

	public SupplierMaster(String supplierName, String advertiserName, String productName) {
		this.supplierName = supplierName;
		this.advertiserName = advertiserName;
		this.productName = productName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "supplier_name", nullable = false, length = 1000)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "advertiser_name", nullable = false, length = 1000)
	public String getAdvertiserName() {
		return this.advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	@Column(name = "product_name", nullable = false, length = 1000)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
