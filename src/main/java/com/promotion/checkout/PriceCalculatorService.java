package com.promotion.checkout;

import java.util.List;

import com.promotion.checkout.domain.Product;

public interface PriceCalculatorService {

	public int calculateProductPrice(List<Product> products);
}
