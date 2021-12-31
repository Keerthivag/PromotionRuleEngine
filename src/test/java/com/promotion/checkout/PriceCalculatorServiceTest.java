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

	private PriceCalculatorService priceCalculatorService;

	private Product productA;
	private Product productB;
	private Product productC;
	private Product productD;

	@Before
	public void setup() {
		priceCalculatorService = new PriceCalculatorServiceImpl();
		productA = new Product("A", 50);
		productB = new Product("B", 30);
		productC = new Product("C", 20);
		productD = new Product("D", 15);
	}

	@Test
	public void testCalculateProductPriceWhenValidProducts() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productB, productC, productD));
		Assert.assertEquals(110, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceWhenPromotionAppliedForSkuIDA() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productA, productA, productB, productC));
		Assert.assertEquals(180, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceWhenPromotionAppliedForSkuIDB() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productB, productB, productC));
		Assert.assertEquals(115, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceWhenPromotionAppliedForSkuIDCandD() {
		List<Product> products = new ArrayList<>(Arrays.asList(productC, productD));
		Assert.assertEquals(30, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceWhenPromotionAppliedForSkuIDAandB() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productA, productA, productB, productB));
		Assert.assertEquals(175, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceForFirstScenario() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productB, productC));
		Assert.assertEquals(100, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceForSecondScenario() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productA, productA, productA, productA,
				productB, productB, productB, productB, productB, productC));
		Assert.assertEquals(370, priceCalculatorService.calculateProductPrice(products));
	}

	@Test
	public void testCalculateProductPriceForThirdScenario() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productA, productA, productB, productB,
				productB, productB, productB, productC, productD));
		Assert.assertEquals(280, priceCalculatorService.calculateProductPrice(products));
	}
	
	@Test
	public void testCalculateProductPriceForComboScenario() {
		List<Product> products = new ArrayList<>(Arrays.asList(productA, productA, productA, productA, productB, productB,
				productB, productB, productB, productC, productD, productC, productD, productC));
		Assert.assertEquals(380, priceCalculatorService.calculateProductPrice(products));
	}

	@Test(expected = ProductEmptyException.class)
	public void testCalculateProductPriceWhenProductIsEmpty() {
		priceCalculatorService.calculateProductPrice(Collections.emptyList());
	}

}
