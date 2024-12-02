package com.supermarketcheckout.service;

import com.supermarketcheckout.exception.InvalidCartException;
import com.supermarketcheckout.model.CheckoutResult;
import com.supermarketcheckout.model.PricingDiscount;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ShoppingCart;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class CheckoutProcessingService {


    public CheckoutResult checkout(ShoppingCart cart) {
        if (cart == null) {
            throw new InvalidCartException("Shopping cart is null");
        }

        Integer total = calculateTotal(cart);
        Integer discount = calculateDiscount(cart);
        Integer toPay = total - discount;

        return new CheckoutResult(total, discount, toPay, cart);
    }


    private Integer calculateTotal(ShoppingCart cart) {
        return cart.getProducts().stream()
                .filter(Objects::nonNull)
                .map(Product::getPrice)
                .collect(Collectors.summingInt(Integer::intValue));
    }

    /**
     * Calculates the total discount for the given shopping cart based on the products and their
     * associated discounts. This method iterates through the products in the cart, counts the occurrences
     * of each product, and checks if a discount is applicable based on the number of units of that product.
     *
     * @param cart The shopping cart containing the products to calculate discounts for.
     * @return The total discount value, in GBP pence, for all applicable products in the cart.
     */

    private Integer calculateDiscount(ShoppingCart cart) {
        Map<Product, Long> countMap = cart.getProducts().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        return countMap.entrySet().stream().map(entry -> {
            Product product = entry.getKey();
            Long count = entry.getValue();
            Optional<PricingDiscount> discount = product.getPricingDiscount();

            if (!discount.isPresent()) {
                return 0;
            } else {
                Long times = count / discount.get().getUnitsForDiscount();
                return discount.get().getDiscountValue() * times.intValue();
            }
        }).collect(Collectors.summingInt(Integer::intValue));
    }


    public Integer calculateRunningTotal(ShoppingCart cart) {
        return cart.getProducts().stream()
                .filter(Objects::nonNull)
                .map(Product::getPrice)
                .collect(Collectors.summingInt(Integer::intValue));
    }


    public Integer calculateRunningTotalAfterDiscount(ShoppingCart cart) {
        Integer total = calculateRunningTotal(cart);
        Integer discount = calculateDiscount(cart);
        return total - discount;
    }
}