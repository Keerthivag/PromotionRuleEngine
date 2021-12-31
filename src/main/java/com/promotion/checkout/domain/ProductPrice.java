package com.promotion.checkout.domain;

public enum ProductPrice {

	A(50), B(30), C(20), D(15);

	private int price;

	private ProductPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

}
