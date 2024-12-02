package com.supermarketcheckout.model;

public class CheckoutResult {

    private Integer total;
    private Integer discount;
    private Integer toPay;
    private ShoppingCart shoppingCart;

    public CheckoutResult(Integer total, Integer discount, Integer toPay, ShoppingCart shoppingCart) {
        this.total = total;
        this.discount = discount;
        this.toPay = toPay;
        this.shoppingCart = shoppingCart;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Integer getToPay() {
        return toPay;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

}
