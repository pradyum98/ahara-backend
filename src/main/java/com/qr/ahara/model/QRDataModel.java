package com.qr.ahara.model;

import java.util.ArrayList;
import java.util.HashMap;

public class QRDataModel {
	
	@DocumentId
    private String couponId;
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public long getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(long validUpto) {
		this.validUpto = validUpto;
	}
	public int getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(int couponValue) {
		this.couponValue = couponValue;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String toString() {
		HashMap<String,Object> obj = new HashMap<String,Object>();
		obj.put("createdAt",createdAt);
		obj.put("couponValue",couponValue);
		obj.put("active",active);
		obj.put("validUpto",validUpto);
		obj.put("couponId",couponId);
		
		obj.put("nameOfCouponHolder",nameOfCouponHolder);
		obj.put("nameOfHospital",nameOfHospital);
		obj.put("location",location);
		obj.put("aadharNumber",aadharNumber);
		obj.put("ipdRegistrationNumber",ipdRegistrationNumber);
		
		return obj.toString();
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getNameOfCouponHolder() {
		return nameOfCouponHolder;
	}
	public void setNameOfCouponHolder(String nameOfCouponHolder) {
		this.nameOfCouponHolder = nameOfCouponHolder;
	}
	public String getNameOfHospital() {
		return nameOfHospital;
	}
	public void setNameOfHospital(String nameOfHospital) {
		this.nameOfHospital = nameOfHospital;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getIpdRegistrationNumber() {
		return ipdRegistrationNumber;
	}
	public void setIpdRegistrationNumber(String ipdRegistrationNumber) {
		this.ipdRegistrationNumber = ipdRegistrationNumber;
	}
	public String getQrCodeImage() {
		return qrCodeImage;
	}
	public String getGenerationId() {
		return generationId;
	}
	public void setGenerationId(String generationId) {
		this.generationId = generationId;
	}
	public void setQrCodeImage(String qrCodeImage) {
		this.qrCodeImage = qrCodeImage;
	}
	private long createdAt;
	private long validUpto;
	private int couponValue;
	private boolean active;
	private String nameOfCouponHolder;
	private String nameOfHospital;
	private String location;
	private String aadharNumber;
	private String ipdRegistrationNumber;
	private String qrCodeImage;
	private String generationId;
}
