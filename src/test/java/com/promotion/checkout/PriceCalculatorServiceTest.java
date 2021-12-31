package com.promotion.checkout;

import org.junit.Before;
import org.junit.Test;

public class PriceCalculatorServiceTest {
	
	PriceCalculatorService priceCalculatorService;
	
	@Before
	public void setup() {
		priceCalculatorService = new PriceCalculatorServiceImpl();
	}
	
	@Test
	public void testCalculateProductPrice() {
		priceCalculatorService.calculateProductPrice();
	}
	

}
