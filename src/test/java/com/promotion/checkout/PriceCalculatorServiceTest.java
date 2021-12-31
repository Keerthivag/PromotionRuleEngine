package com.promotion.checkout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.promotion.checkout.domain.Product;

public class PriceCalculatorServiceTest {

	PriceCalculatorService priceCalculatorService;

	@Before
	public void setup() {
		priceCalculatorService = new PriceCalculatorServiceImpl();
	}

	@Test
	public void testCalculateProductPrice() {
		Product productA = new Product("A", 50);
		Product productB = new Product("B", 30);
		Product productC = new Product("C", 20);
		Product productD = new Product("D", 15);
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productB, productC, productD));
		priceCalculatorService.calculateProductPrice(products);
	}

}
