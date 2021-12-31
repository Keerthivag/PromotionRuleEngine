package com.promotion.checkout.domain;

public class Product {
	
	private String skuId;
	
	private int price;
	
	public Product(String skuId, int price) {
		super();
		this.skuId = skuId;
		this.price = price;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
