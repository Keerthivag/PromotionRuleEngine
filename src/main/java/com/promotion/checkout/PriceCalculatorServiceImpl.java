package com.promotion.checkout;

import java.util.List;

import com.promotion.checkout.domain.Product;
import com.promotion.checkout.exception.ProductEmptyException;

public class PriceCalculatorServiceImpl implements PriceCalculatorService {

	@Override
	public int calculateProductPrice(List<Product> products) {

		if (null == products || products.isEmpty()) {
			throw new ProductEmptyException("Product List is Empty");
		}
		return 0;
	}

}
