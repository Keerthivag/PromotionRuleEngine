package com.promotion.checkout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.promotion.checkout.domain.Product;
import com.promotion.checkout.domain.ProductPrice;
import com.promotion.checkout.domain.PromotionPrice;
import com.promotion.checkout.exception.ProductEmptyException;

public class PriceCalculatorServiceImpl implements PriceCalculatorService {

	@Override
	public int calculateProductPrice(List<Product> products) {
		AtomicInteger totalPrice = new AtomicInteger(0);
		try {
			if (null == products || products.isEmpty()) {
				throw new ProductEmptyException("Product List is empty");
			} else {
				totalPrice.addAndGet(applypromotionPriceForCAndD(products));

				products.stream().collect(Collectors.groupingBy(Product::getSkuId)).forEach((skuId, prods) -> {
					totalPrice.addAndGet(applypromotionPriceForAorB(skuId, prods));
				});
			}
		} catch (Exception ex) {
			throw new ProductEmptyException("Arithmetic error occured :: " + ex);
		}
		return totalPrice.get();
	}

	private int applypromotionPriceForAorB(String skuId, List<Product> products) {
		int totalPrice = 0;
		int skuIdQty = products.size();
		if (null != PromotionPrice.valueOf(skuId) && null != ProductPrice.valueOf(skuId)) {
			int prodPrice = ProductPrice.valueOf(skuId).getPrice();
			int totalQty = PromotionPrice.valueOf(skuId).getQty();
			int offerprice = PromotionPrice.valueOf(skuId).getPrice();
			totalPrice = (skuIdQty / totalQty) * offerprice + (skuIdQty % totalQty * prodPrice);
		} else {
			totalPrice = products.stream().collect(Collectors.summingInt(Product::getPrice));
		}
		return totalPrice;
	}

	private int applypromotionPriceForCAndD(List<Product> products) {

		int totalPrice = 0;
		String skuIdC = ProductPrice.C.name();
		String skuIdD = ProductPrice.D.name();

		long skuIdOfC = products.stream().filter(prod -> prod.getSkuId().equalsIgnoreCase(skuIdC)).count();
		long skuIdOfD = products.stream().filter(prod -> prod.getSkuId().equalsIgnoreCase(skuIdD)).count();

		if (skuIdOfC == skuIdOfD) {
			int promotionPrice = PromotionPrice.CD.getPrice();
			totalPrice += skuIdOfD * promotionPrice;
		} else {
			long minCOrD = Math.min(skuIdOfC, skuIdOfD);
			int promotionPrice = PromotionPrice.CD.getPrice();
			totalPrice += minCOrD * promotionPrice;
			if (skuIdOfC > skuIdOfD) {
				totalPrice += calculatePriceForCAndD(skuIdC, skuIdOfC, skuIdOfD);
			} else {
				totalPrice += calculatePriceForCAndD(skuIdD, skuIdOfD, skuIdOfC);
			}
		}
		products.removeIf(
				(Product p) -> p.getSkuId().equalsIgnoreCase(skuIdC) || p.getSkuId().equalsIgnoreCase(skuIdD));
		return totalPrice;

	}

	private long calculatePriceForCAndD(String sku, long sku1, long sku2) {
		int prodPrice = ProductPrice.valueOf(sku).getPrice();
		return (sku1 - sku2) * prodPrice;
	}

}
