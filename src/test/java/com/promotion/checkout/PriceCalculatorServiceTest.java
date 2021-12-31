package com.promotion.checkout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.promotion.checkout.domain.Product;
import com.promotion.checkout.exception.ProductEmptyException;

public class PriceCalculatorServiceTest {

	PriceCalculatorService priceCalculatorService;

	@Before
	public void setup() {
		priceCalculatorService = new PriceCalculatorServiceImpl();
	}

	@Test
	public void testCalculateProductPriceWhenValidProducts() {
		Product productA = new Product("A", 50);
		Product productB = new Product("B", 30);
		Product productC = new Product("C", 20);
		Product productD = new Product("D", 15);
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productB, productC, productD));
		Assert.assertEquals(110, priceCalculatorService.calculateProductPrice(products));
	}
	
	@Test(expected = ProductEmptyException.class)
	public void testCalculateProductPriceWhenProductIsEmpty() {
		priceCalculatorService.calculateProductPrice(Collections.emptyList());
	}

}
