package com.supermarketcheckout.service;

import com.supermarketcheckout.exception.InvalidCartException;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ShoppingCart;

public class BarcodeScannerService {

    public ShoppingCart scan(Product product, ShoppingCart cart) {
        if (cart == null) {
            throw new InvalidCartException("Cart is null");
        }

        if (product != null) {
            cart.getProducts().add(product);
        }

        return cart;
    }
}
