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
		if (null == products || products.isEmpty()) {
			throw new ProductEmptyException("Product List is empty");
		} else {
			String skuIdC = ProductPrice.C.name();
			String skuIdD = ProductPrice.D.name();

			int skuIdOfC = (int) products.stream().filter(prod -> prod.getSkuId().equalsIgnoreCase(skuIdC)).count();
			int skuIdOfD = (int) products.stream().filter(prod -> prod.getSkuId().equalsIgnoreCase(skuIdD)).count();

			if (skuIdOfC == skuIdOfD) {
				int promotionPrice = PromotionPrice.CD.getPrice();
				totalPrice.getAndAdd(skuIdOfD * promotionPrice);
			} else {
				int minCOrD = Math.min(skuIdOfC, skuIdOfD);
				int promotionPrice = PromotionPrice.CD.getPrice();
				totalPrice.getAndAdd(minCOrD * promotionPrice);
				if (skuIdOfC > skuIdOfD) {
					int prodPrice = ProductPrice.valueOf(skuIdC).getPrice();
					totalPrice.getAndAdd((skuIdOfC - skuIdOfD) * prodPrice);
				} else {
					int prodPrice = ProductPrice.valueOf(skuIdD).getPrice();
					totalPrice.getAndAdd((skuIdOfD - skuIdOfC) * prodPrice);
				}
			}

			products.stream().collect(Collectors.groupingBy(Product::getSkuId)).forEach((skuId, prods) -> {
				int skuIdQty = prods.size();
				if (null != PromotionPrice.valueOf(skuId) && null != ProductPrice.valueOf(skuId)) {
					int prodPrice = ProductPrice.valueOf(skuId).getPrice();
					int totalQty = PromotionPrice.valueOf(skuId).getQty();
					int offerprice = PromotionPrice.valueOf(skuId).getPrice();
					totalPrice.getAndAdd((skuIdQty / totalQty) * offerprice + (skuIdQty % totalQty * prodPrice));
				} else {
					totalPrice.getAndAdd(products.stream().collect(Collectors.summingInt(Product::getPrice)));
				}
			});
		}
		return totalPrice.get();
	}

}
