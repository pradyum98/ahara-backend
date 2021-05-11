package com.qr.ahara.model;

import java.util.HashMap;

import com.google.cloud.firestore.annotation.DocumentId;

public class QRDataModel {
	
	@DocumentId
    private long couponId;
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
		
		return obj.toString();
	}
	private long createdAt;
	private long validUpto;
	private int couponValue;
	private boolean active;

}
