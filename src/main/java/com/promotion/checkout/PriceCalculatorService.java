package com.promotion.checkout;

import java.util.List;

public interface PriceCalculatorService {

	public int calculateProductPrice(List<Product> products);
}
