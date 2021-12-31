package com.promotion.checkout.domain;

public enum PromotionPrice {

	A(3, 130), B(2, 45), CD(1, 30);

	private int qty;

	private int price;

	private PromotionPrice(int qty, int price) {
		this.qty = qty;
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
