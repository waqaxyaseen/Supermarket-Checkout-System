package com.supermarketcheckout.service;

import com.supermarketcheckout.exception.InvalidCartException;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BarcodeScannerServiceTest {

    @InjectMocks
    private BarcodeScannerService barcodeScannerService;


    @Test
    public void scan() {
        ShoppingCart cart = new ShoppingCart();
        cart = barcodeScannerService.scan(productA(), cart);

        assertThat(cart.getProducts()).containsExactly(productA());
    }

    @Test
    public void scanMultipleDifferentProducts() {
        ShoppingCart cart = new ShoppingCart();
        cart = barcodeScannerService.scan(productA(), cart);
        cart = barcodeScannerService.scan(productB(), cart);

        assertThat(cart.getProducts()).containsExactly(productA(), productB());
    }

    @Test
    public void scanDuplicateProducts() {
        ShoppingCart cart = new ShoppingCart();
        cart = barcodeScannerService.scan(productA(), cart);
        cart = barcodeScannerService.scan(productA(), cart);

        assertThat(cart.getProducts()).containsExactly(productA(), productA());
    }

    @Test
    public void scanFilterNulls() {
        ShoppingCart cart = new ShoppingCart();
        cart = barcodeScannerService.scan(productA(), cart);
        cart = barcodeScannerService.scan(null, cart);

        assertThat(cart.getProducts()).containsExactly(productA());
    }

    @Test
    public void failWhenCartIsNull() {
        Assertions.assertThrows(InvalidCartException.class,() -> barcodeScannerService.scan(productA(), null));
    }

    private Product productA() {
        return new Product("A", 50);
    }

    private Product productB() {
        return new Product("B", 30);
    }
}
