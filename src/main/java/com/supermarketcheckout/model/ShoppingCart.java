package com.supermarketcheckout.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public String toString() {
        return "ShoppingCart [products=" + products + "]";
    }
}
