package com.promotion.checkout.exception;

public class ProductEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductEmptyException(String message) {
		super(message);
	}

}